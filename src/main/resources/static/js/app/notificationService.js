/**
 * Created by Andrii on 20.06.2015.
 */
'use strict';
servicesModule.factory('NotificationService', ['toaster', function(toaster){
    return {
        error: function (message) {
            toaster.pop('error', "Error", message);
        }
    };
}]);