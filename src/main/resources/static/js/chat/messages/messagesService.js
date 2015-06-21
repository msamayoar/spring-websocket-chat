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

    var reorderMessages = function () {
        messages.sort(function (mes1, mes2) {
            return mes1.time - mes2.time;
        });
    };

    var pushMessage = function (message, status) {
        var _status = status ? status : appConst.CHAT.MESSAGE.STATUS.PENDING;
        message.status = _status;
        messages.push(message);
    };

    var addMessage = function (message, status) {
        pushMessage(message, status);
        reorderMessages();
        notifyMessagesUpdated();
    };
    
    var findMessage = function (packetId) {
        for (var i = 0; i < messages.length; i++) {
            var obj = messages[i];
            if(obj.packetId == packetId){
                return messages[i];
            }
        }
        return null;
    };

    var loadMessages = function (contact) {
        messages = [];
        //TODO: load older messages for the contact
        notifyMessagesUpdated();
    };

    return {
        get: function () { return messages; },
        set: function (_messages) { messages = _messages; },
        addMessage: function(message, status) { addMessage(message, status); },
        messageDelivered: function (deliveryStatus) {
            var status = JSON.parse(deliveryStatus.body);
            var message = findMessage(status.packetId);
            if(message){
                message.status = status.status;
                message.uuid = status.uuid;
                message.time = status.time ? status.time : message.time;
            }
        },
        switchConversation: function (contact) { loadMessages(contact); },
        initSubscription: function () {
            chatSocket.subscribe(
                paths.CHAT.INCOMING_SUB,
                function (messageStr) {
                    var message = JSON.parse(messageStr.body);
                    confirmPrivateMessageDelivery(message);
                    message.priv = true;
                    addMessage(message, appConst.CHAT.MESSAGE.STATUS.DELIVERED);
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
