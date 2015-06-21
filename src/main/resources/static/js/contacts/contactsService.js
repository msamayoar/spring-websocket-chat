/**
 * Created by Andrii on 20.06.2015.
 */
'use strict';
servicesModule.factory('ContactsService', ['AppEvents', 'ChatSocket', function(appEvents, chatSocket) {
    var contacts = [];
    var selectedContact = {};

    var contactTypes = {
        USER: 0,
        GROUP: 1
    };

    var fetchContacts = function () {
        chatSocket.send(paths.CONTACTS.GET_SEND);
    };

    var parseContacts = function(message) {
        contacts = JSON.parse(message.body);
    };

    var getContacts = function () {
        return contacts;
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
        types: contactTypes,
        selectContact: function (contact) { selectedContact = contact; notifyContactSelected(); },
        initSubscription: function () {
            chatSocket.subscribe(
                paths.CONTACTS.ALL_SUB,
                function(message) {
                    parseContacts(message);
                    notifyContactsUpdated();
                }
            );
        },
        initData: function () { fetchContacts(); }
    }
}]);