/**
 * Created by Andrii on 20.06.2015.
 */
'use strict';
servicesModule.factory('ContactsService', ['AppEvents', 'ChatSocket', 'ChatService', function(appEvents, chatSocket, chatService) {
    var contacts = [];
    var selectedContact = {};

    /*
    var contactTypes = {
        USER: 0,
        GROUP: 1
    };
    */

    var fetchContacts = function () {
        chatSocket.send(paths.CONTACTS.GET_SEND);
    };

    var parseContacts = function(message) {
        contacts = JSON.parse(message.body);
    };

    var reorderContacts = function () {
        contacts.sort(function (c1, c2) {
            if (c1.signature < c2.signature)
                return -1;
            if (c1.signature > c2.signature)
                return 1;
            return 0;
        });
    };

    var getContacts = function () {
        return contacts;
    };

    var sendInviteContactsRequest = function(login) {
        chatSocket.send(
            paths.CONTACTS.INVITE_CONTACT_REQUEST_SEND,
            {},
            JSON.stringify(
                {
                    recipient: login
                }
            )
        )
    };

    var sendInviteContactReply = function(request, status) {
        var reply = angular.copy(request);
        reply.status = status;

        chatSocket.send(
            paths.CONTACTS.INVITE_CONTACT_REPLY_SEND,
            {},
            JSON.stringify(reply)
        )
    };

    var processInviteContactRequest = function(frame) {
        var request = JSON.parse(frame.body);
        console.log(request);
        sendInviteContactReply(request, appConst.CONTACTS.REQUEST.STATUS.APPROVED);
    };

    var processInviteContactReply = function(frame) {
        console.log(JSON.parse(frame.body));
    };

    var notifyContactsUpdated = function () {
        appEvents.fire(appEvents.CONTACTS.CHANGED);
    };

    var notifyContactSelected = function () {
        appEvents.fire(appEvents.CONTACTS.SELECTED);
    };

    return {
        set: function(_contacts){ contacts = _contacts; },
        get: function () { return getContacts(); },
        selected: function () { return selectedContact; },
        parse: function (message) { parseContacts(message); },
        fetch: function () { fetchContacts(); },
        notifyContactSelected: function () { notifyContactsUpdated(); },
        amount: function(){ return contacts.length; },
        types: function() {
            return appConst.CONTACTS.TYPE;
        },
        selectContact: function (contact) { selectedContact = contact; chatService.switchConversation(contact); },
        sendInviteUserToContactsRequest: function(login) {
            sendInviteContactsRequest(login);
        },
        initSubscription: function () {
            chatSocket.subscribe(
                paths.CONTACTS.ALL_SUB,
                function(message) {
                    parseContacts(message);
                    reorderContacts();
                    notifyContactsUpdated();
                }
            );

            chatSocket.subscribe(
                paths.CONTACTS.INVITE_CONTACT_REQUEST_SUB,
                function(frame) {
                    processInviteContactRequest(frame);
                }
            );

            chatSocket.subscribe(
                paths.CONTACTS.INVITE_CONTACT_REPLY_SUB,
                function(frame) {
                    processInviteContactReply(frame);
                }
            )
        },
        initData: function () { fetchContacts(); }
    }
}]);