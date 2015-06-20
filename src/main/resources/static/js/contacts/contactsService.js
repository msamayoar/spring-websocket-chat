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

    var fetchCts = function () {
        chatSocket.send(paths.CONTACTS.GET_SEND);
    };

    var parseCts = function(message) {
        contacts = JSON.parse(message.body);
    };

    var getCts = function () {
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
        get: function () { return getCts(); },
        selected: function () { return selectedContact; },
        parse: function (message) { parseCts(message); },
        fetch: function () { fetchCts(); },
        notifyContactSelected: function () { notifyContactsUpdated(); },
        amount: function(){ return contacts.length; },
        types: contactTypes,
        selectContact: function (contact) { selectedContact = contact; notifyContactSelected(); },
        initSubscription: function () {
            chatSocket.subscribe(
                paths.CONTACTS.ALL_SUB,
                function(message) {
                    parseCts(message);
                    notifyContactsUpdated();
                }
            );
        },
        initData: function () { fetchCts(); }
    }
}]);