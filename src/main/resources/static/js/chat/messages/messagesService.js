/**
 * Created by Andrii on 21.06.2015.
 */
'use strict';

servicesModule.factory('MessagesService', ['$injector', 'AppEvents', 'ChatSocket', 'UserService', function($injector, appEvents, chatSocket, userService) {
    var messages = [];

    var queuedMessages = [];
    
    var parseMessages = function (_messages) {
        messages = JSON.parse(_messages);
    };

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
            if(obj.packetId === packetId){
                return messages[i];
            }
        }
        return null;
    };

    var loadMessages = function (contact) {
        messages = [];
        chatSocket.send(
            paths.SYNC.PARTICIPANT_SEND,
            {},
            JSON.stringify({
                signature: contact.signature,
                type: contact.type
            })
        );
        notifyMessagesUpdated();
    };

    var addMessageToQueue = function (message) {
        //TODO: Group queued messages by contact to highlight "recently received" messages in chat view
        queuedMessages.push(message);
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
                    debugger;
                    var message = JSON.parse(messageStr.body);
                    var contactsService = $injector.get("ContactsService");

                    //TODO: Fix inconsistency in message json: recipient/to. Should be either "recipient" or "to"

                    if(message.from !== contactsService.selected().signature && (message.to !== contactsService.selected().signature
                            || message.recipient !== contactsService.selected().signature)) {
                        var contactUsername = "";
                        var contactType = 0;
                        if(message.recipientType === appConst.CONTACTS.TYPE.USER) {
                            contactUsername = message.from;
                            contactType = appConst.CONTACTS.TYPE.USER;
                        } else if(message.recipientType === appConst.CONTACTS.TYPE.GROUP) {
                            contactUsername = message.to ? message.to : message.recipient;
                            contactType = appConst.CONTACTS.TYPE.GROUP;
                        }
                        contactsService.incrementQueuedMessagesAmount(contactUsername, contactType);
                        addMessageToQueue(message);
                    } else {
                        confirmPrivateMessageDelivery(message);
                        message.priv = true;
                        addMessage(message, appConst.CHAT.MESSAGE.STATUS.DELIVERED);
                    }
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

            chatSocket.subscribe(
                paths.SYNC.PARTICIPANT_SUB,
                function (frame) {
                    parseMessages(frame.body);
                    reorderMessages();
                    notifyMessagesUpdated();
                }
            );
        }
    }
}]);
