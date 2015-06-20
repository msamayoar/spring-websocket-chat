/**
 * Created by Andrii on 21.06.2015.
 */
'use strict';

servicesModule.factory('MessagesService', ['AppEvents', 'ChatSocket', 'UserService', function(appEvents, chatSocket, userService) {
    var messages = [];

    var confirmDeliveryStatus = function (from, recipientType, packetId, uuid, status) {
        chatSocket.send(
            paths.CHAT.DELIVERY_SEND,
            {},
            JSON.stringify(
                {
                    to: userService.username(),
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

    var notifyMessagesUpdated = function () {
        appEvents.fire(appEvents.CHAT.MESSAGES.CHANGED);
    };

    return {
        get: function () { return messages; },
        set: function (_messages) { messages = _messages; },
        initSubscription: function () {
            chatSocket.subscribe(
                paths.CHAT.INCOMING_SUB,
                function (messageStr) {
                    var message = JSON.parse(messageStr.body);
                    confirmPrivateMessageDelivery(message);
                    message.priv = true;
                    messages.unshift(message);
                    notifyMessagesUpdated();
                }
            );

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
