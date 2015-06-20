/**
 * Created by Andrii on 20.06.2015.
 */
'use strict';
servicesModule.factory('ChatService', ['AppEvents', 'ChatSocket', 'NotificationService', 'UserService', function(appEvents, chatSocket, notification, userService) {
    var conversation = {
        participants: [],
        message: ""
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
        chatSocket.send(
            destination,
            {},
            JSON.stringify(
                {
                    from: userService.username(),
                    recipientType: recipientType,
                    recipient: recipient,

                    subject: subject,
                    text: text,

                    packetId: makeId()
                }
            )
        )
    };

    var sendPrivateMessage = function(recipient, subject, text) {
        sendGenericChatMessage(paths.CHAT.PRIVATE_SEND, recipient, appConst.RECIPIENT.TYPE.USER, subject, text);
    };

    return {
        participants: conversation.participants,
        notifyConversationUpdated: function(){ notifyConversationUpdated(); },

        sendMessage: function (recipient, recipientType, subject, text) {
            sendPrivateMessage(recipient, recipientType, subject, text);
        },

        initSubscription: function () {

            chatSocket.subscribe(
                paths.CHAT.DELIVERY_SUB,
                function(deliveryStatus) {
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
                notifyConversationUpdated();
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
                    notifyConversationUpdated();
                }
            );

            chatSocket.subscribe("/user/queue/errors", function (message) {
                notification.error(message.body);
            });
        }
    }
}]);