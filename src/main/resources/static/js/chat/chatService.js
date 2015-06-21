/**
 * Created by Andrii on 20.06.2015.
 */
'use strict';
servicesModule.factory('ChatService', ['AppEvents', 'ChatSocket', 'NotificationService', 'UserService', 'MessagesService', function(appEvents, chatSocket, notification, userService, messagesService) {
    var conversation = {
        contact: {},
        participants: []
    };

    var getPrefix = function () {
        var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        var text = "";

        for( var i=0; i < 5; i++ )
            text += possible.charAt(Math.floor(Math.random() * possible.length));

        return text;
    };

    var packetId = {
        prefix: "",
        id: 0
    };

    packetId.prefix = getPrefix();

    var makeId = function() {
        return packetId.prefix + "-" + packetId.id++;
    };

    var notifyConversationUpdated = function () { appEvents.fire(appEvents.CHAT.CONVERSATION.CHANGED); };

    var sendGenericChatMessage = function(destination, recipient, recipientType, subject, text) {
        var message = {
            from: userService.username(),
            recipientType: recipientType,
            recipient: recipient,

            subject: subject,
            text: text,

            packetId: makeId()
        };

        chatSocket.send(
            destination,
            {},
            JSON.stringify(message)
        );
        message.time = new Date().getTime();
        messagesService.addMessage(message);
    };

    var sendPrivateMessage = function(recipient, subject, text) {
        sendGenericChatMessage(paths.CHAT.PRIVATE_SEND, recipient, appConst.CHAT.PARTICIPANT.TYPE.USER, subject, text);
    };

    return {
        conversation: conversation,
        getParticipants: function () { return conversation.participants; },
        getContact: function () { return conversation.contact; },
        notifyConversationUpdated: function(){ notifyConversationUpdated(); },

        switchConversation: function (contact) {
            conversation.contact = contact;
            switch(contact.type) {
                case appConst.CHAT.PARTICIPANT.TYPE.USER:
                    break;
                case appConst.CHAT.PARTICIPANT.TYPE.GROUP:
                    //TODO: Load participants
                    break;
            }
            messagesService.switchConversation(contact);
            notifyConversationUpdated();
        },

        sendMessage: function (recipient, recipientType, subject, text) {
            sendPrivateMessage(recipient, subject, text);
        },

        initSubscription: function () {

            chatSocket.subscribe(
                paths.CHAT.DELIVERY_SUB,
                function(deliveryStatus) {
                    messagesService.messageDelivered(deliveryStatus);
                    console.log(JSON.parse(deliveryStatus.body));
                }
            );

            chatSocket.subscribe("/topic/chat.typing", function (message) {
                var parsed = JSON.parse(message.body);
                if (parsed.username == userService.username()) return;

                for (var index in conversation.participants) {
                    var participant = conversation.participants[index];

                    if (participant.username == parsed.username) {
                        conversation.participants[index].typing = parsed.typing;
                    }
                }
                //notifyConversationUpdated();
            });

            chatSocket.subscribe(
                paths.PRESENCE.PRESENCE_SUB,
                function (message) {
                    var presence = JSON.parse(message.body);
                    var presenceStatus = presence.status;
                    var login = presence.login;
                    console.log("Presence '" + presenceStatus + "' received from '" + login + "'");
                    if (presenceStatus == "offline") {
                        for (var index in conversation.participants) {
                            if (conversation.participants[index].username == login) {
                                conversation.participants.splice(index, 1);
                            }
                        }
                    } else if (presenceStatus == "online") {
                        conversation.participants.unshift({username: presence.login, typing: false});
                    }
                    //notifyConversationUpdated();
                }
            );

            chatSocket.subscribe(
                /*"/user/queue/errors",*/
                paths.ERRORS.SUB,
                function (message) {
                    notification.error(message.body);
                }
            );

            chatSocket.subscribe(
                /*"/user/queue/sync/messages",*/
                paths.SYNC.ALL_MESSAGES_SUB,
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

            chatSocket.subscribe(
                paths.SYNC.PARTICIPANT_SUB,
                function (frame) {
                    var messages = JSON.parse(frame.body);

                    var length = messages.length;
                    for (var i = 0; i < length; i++) {
                        console.log(messages[i]);
                    }
                }
            )
        }
    }
}]);