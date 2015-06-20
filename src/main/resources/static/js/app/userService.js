/**
 * Created by Andrii on 20.06.2015.
 */
'use strict';
servicesModule.factory('UserService', ['AppEvents', function(appEvents) {
    var user = {
        username: ""
    };

    return {
        username: function () { return user.username; },
        user: user,
        notifyUpdated: function () { appEvents.fire(appEvents.USER.CHANGED); }
    }
}]);