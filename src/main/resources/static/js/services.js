'use strict';

/* Services */

var servicesModule = angular.module('springChat.services', []);

servicesModule.factory('EventConst', function(){
	return {
		CONVERSATION_CHANGED: "conversationChanged",
		USER_CHANGED: "userChanged",
		CONTACTS_CHANGED: "contactsChanged"
	};
});

servicesModule.factory('NotificationService', ['toaster', function(toaster){
	return {
		error: function (message) {
			toaster.pop('error', "Error", message);
		},
		USER_CHANGED: "userChanged",
		CONTACTS_CHANGED: "contactsChanged"
	};
}]);

servicesModule.factory('ChatSocket', ['$rootScope', function($rootScope) {
	var stompClient;

	var wrappedSocket = {

		init: function(url) {
			stompClient = Stomp.over(new SockJS(url));
		},
		connect: function(successCallback, errorCallback) {

			stompClient.connect({}, function(frame) {
				$rootScope.$apply(function() {
					successCallback(frame);
				});
			}, function(error) {
				$rootScope.$apply(function(){
					errorCallback(error);
				});
			});
		},
		subscribe : function(destination, callback) {
			stompClient.subscribe(destination, function(message) {
				$rootScope.$apply(function(){
					callback(message);
				});
			});
		},
		send: function(destination, headers, object) {
			stompClient.send(destination, headers, object);
		}
	};

	return wrappedSocket;

}]);

servicesModule.factory('SocketService', ['ChatSocket', 'ChatService', 'ContactsService', 'NotificationService', function(chatSocket, chat, contacts, notification) {

	var initStompClient = function () {
		chatSocket.init('/ws');

		chatSocket.connect(function (frame) {
			chat.user.username = frame.headers['user-name'];
			chat.updateUser();

			chat.initSubscription();
			contacts.initSubscription();

			contacts.fetch();
		}, function (error) {
			notification.error('Connection error ' + error);
		});
	};

	return {
		init: function () {
			initStompClient();
		}
	}
}]);

servicesModule.factory('ContactsService', ['$rootScope', 'EventConst', 'ChatSocket', function($rootScope, eventConst, chatSocket) {
	var contacts = [];

	var contactTypes = {
		USER: 0,
		GROUP: 1
	};

	var fetchCts = function () {
		chatSocket.send("/app/contacts/get");
	};

	var parseCts = function(message) {
		contacts = JSON.parse(message.body);
	};

	var getCts = function () {
		return contacts;
	};

	var updateCts = function () {
		$rootScope.$broadcast(eventConst.CONTACTS_CHANGED);
	};

	return {
		set: function(_contacts){ contacts = _contacts; },
		get: function () { return getCts(); },
		parse: function (message) { parseCts(message); },
		fetch: function () { fetchCts(); },
		updateContactsView: function () { updateCts(); },
		amount: function(){ return contacts.length; },
		types: contactTypes,
		initSubscription: function () {
			chatSocket.subscribe(
				"/user/queue/contacts/",
				function(message) {
					parseCts(message);
					updateCts();
				}
			);
		}
	}
}]);

servicesModule.factory('ChatService', ['$rootScope', 'EventConst', 'ChatSocket', 'NotificationService', function($rootScope, eventConst, chatSocket, notification) {
	var user = {
		username: ""
	};

	var conversation = {
		participants: [],
		message: "",
		messages: []
	};
	
	var updateConversation = function () { $rootScope.$broadcast(eventConst.CONVERSATION_CHANGED); };
	var updateUser = function () { $rootScope.$broadcast(eventConst.USER_CHANGED); };

	return {
		user: user,
		participants: conversation.participants,
		messages: conversation.messages,
		updateConversation: function(){ updateConversation(); },
		updateUser: function () { updateUser(); },
		initSubscription: function () {
			chatSocket.subscribe("/app/chat.participants", function (message) {
				var logins = JSON.parse(message.body);
				for(var i = 0; i < logins.length; i++) {
					conversation.participants.unshift({username: logins[i], typing: false});
				}
				updateConversation();
			});

			chatSocket.subscribe("/topic/chat.login", function (message) {
				console.log("in subscribe callback on /topic/chat.login");
				conversation.participants.unshift({username: JSON.parse(message.body).username, typing: false});
				updateConversation();
			});

			chatSocket.subscribe("/topic/chat.logout", function (message) {
				var username = JSON.parse(message.body).username;
				for (var index in conversation.participants) {
					if (conversation.participants[index].username == username) {
						conversation.participants.splice(index, 1);
					}
				}
				updateConversation();
			});

			chatSocket.subscribe("/topic/chat.typing", function (message) {
				var parsed = JSON.parse(message.body);
				if (parsed.username == user.username) return;

				for (var index in conversation.participants) {
					var participant = conversation.participants[index];

					if (participant.username == parsed.username) {
						conversation.participants[index].typing = parsed.typing;
					}
				}
				updateConversation();
			});

			chatSocket.subscribe("/topic/presence", function (message) {
				var presence = JSON.parse(message.body);
				var presenceStatus = presence.status;
				var login = presence.login;
				console.log("Presence '" + presenceStatus + "' received from '" + login + "'");
				if(presenceStatus == "offline") {
					for (var index in conversation.participants) {
						if (conversation.participants[index].username == login) {
							conversation.participants.splice(index, 1);
						}
					}
				} else if(presenceStatus == "online") {
					conversation.participants.unshift({username: presence.login, typing: false});
				}
				updateConversation();
			});

			chatSocket.subscribe("/topic/chat.message", function (message) {
				conversation.messages.unshift(JSON.parse(message.body));
				updateConversation();
			});

			chatSocket.subscribe("/user/queue/chat.message", function (message) {
				var parsed = JSON.parse(message.body);
				parsed.priv = true;
				conversation.messages.unshift(parsed);
				updateConversation();
			});

			chatSocket.subscribe("/user/queue/errors", function (message) {
				notification.error(message.body);
			});

			chatSocket.subscribe("/user/queue/sync/messages",
				function (message) {
					var messages = JSON.parse(message.body);

					log("sync result = " + messages);

					var length = messages.length;
					for (var i = 0; i < length; i++) {
						log(messages[i]);
					}

					for (i = messages.length; i--;) {
						log("! " + messages[i]);
					}
				}
			);
		}
	}
}]);