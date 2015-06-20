/**
 * Created by Andrii on 20.06.2015.
 */
'use strict';
servicesModule.factory('ChatService', ['AppEvents', 'ChatSocket', 'NotificationService', 'Paths', function(appEvents, chatSocket, notification, paths) {
    var user = {
        username: ""
    };

    var conversation = {
        participants: [],
        message: "",
        messages: []
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

    var updateConversation = function () { appEvents.fire(appEvents.CONVERSATION_CHANGED); };
    var updateUser = function () { appEvents.fire(appEvents.USER_CHANGED); };

    var sendGenericChatMessage = function(destination, recipient, recipientType, subject, text) {
        chatSocket.send(
            destination,
            {},
            JSON.stringify(
                {
                    from: user.username,
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
        sendGenericChatMessage(paths.CHAT.PRIVATE_SEND, recipient, 0, subject, text);
    };

    var confirmDeliveryStatus = function (from, recipientType, packetId, uuid, status) {
        chatSocket.send(
            paths.CHAT.DELIVERY_SEND,
            {},
            JSON.stringify(
                {
                    to: user.username,
                    from: from,
                    packetId: packetId,
                    recipientType: recipientType,
                    uuid: uuid,
                    status: status
                }
            )
        )
    };

    var confirmPrivateMessageDelivery = function(message) {
        confirmDeliveryStatus(message.from, 0, message.packetId, message.uuid, 1);
    };

    return {
        user: user,
        participants: conversation.participants,
        messages: conversation.messages,
        updateConversation: function(){ updateConversation(); },

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
                if (parsed.username == user.username) return;

                for (var index in conversation.participants) {
                    var participant = conversation.participants[index];

                    if (participant.username == parsed.username) {
                        conversation.participants[index].typing = parsed.typing;
                    }
                }
                updateConversation();
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
                    updateConversation();
                }
            );

            chatSocket.subscribe(
                paths.CHAT.INCOMING_SUB,
                function (messageStr) {
                    var message = JSON.parse(messageStr.body);
                    confirmPrivateMessageDelivery(message);
                    message.priv = true;
                    conversation.messages.unshift(message);
                    updateConversation();
                }
            );

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