/**
 * Created by Andrii on 20.06.2015.
 */
'use strict';
servicesModule.factory('UserService', ['AppEvents', function(appEvents) {
    var user = {
        username: ""
    };

    return {
        user: user,
        update: function () { appEvents.fire(appEvents.USER_CHANGED); }
    }
}]);