/**
 * Created by Andrii on 20.06.2015.
 */
'use strict';
servicesModule.factory('ContactsService', ['AppEvents', 'ChatSocket', 'Paths', function(appEvents, chatSocket, paths) {
    var contacts = [];
    var selectedContact = "";

    var contactTypes = {
        USER: 0,
        GROUP: 1
    };

    var fetchCts = function () {
        chatSocket.send(paths.CONTACTS.GET_SEND/* "/app/contacts/get" */);
    };

    var parseCts = function(message) {
        contacts = JSON.parse(message.body);
    };

    var getCts = function () {
        return contacts;
    };

    var updateCts = function () {
        appEvents.fire(appEvents.CONTACTS_CHANGED);
    };

    return {
        set: function(_contacts){ contacts = _contacts; },
        get: function () { return getCts(); },
        parse: function (message) { parseCts(message); },
        fetch: function () { fetchCts(); },
        updateContactsView: function () { updateCts(); },
        amount: function(){ return contacts.length; },
        types: contactTypes,
        selectContact: function (contact) { selectedContact = contact; },
        initSubscription: function () {
            chatSocket.subscribe(
                paths.CONTACTS.ALL_SUB,
                function(message) {
                    parseCts(message);
                    updateCts();
                }
            );
        },
        initData: function () { fetchCts(); }
    }
}]);