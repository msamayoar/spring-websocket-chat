/**
 * Created by Yurii on 2015-06-21.
 */
'use strict';
servicesModule.factory('SearchService', ['AppEvents', 'ChatSocket', function(appEvents, chatSocket) {
    var foundUsers = {
        users: []
    };

    var parseUsers = function(frame) {
        foundUsers.users = JSON.parse(frame.body);
        console.log(foundUsers.users);
    };

    var searchUsersByNick = function(login) {
        chatSocket.send(
            paths.USERS.MATCHING_SEND,
            {},
            JSON.stringify(
                {
                    signature: login,
                    type: appConst.CONTACTS.TYPE.USER
                }
            )
        )
    };

    var notifyUsersFound = function () {
        appEvents.fire(appEvents.USER.FOUND);
    };

    var contactsRequest = function(login) {
        chatSocket.send(
            paths.USERS
        )
    };

    return {
        getFound: function () { return foundUsers.users; },
        initSubscription: function() {
            chatSocket.subscribe(
                paths.USERS.MATCHING_SUB,
                function(frame) {
                    parseUsers(frame);
                    notifyUsersFound()
                }
            )
        },
        searchUsers: function(login) {
            searchUsersByNick(login);
        }
    }
}]);