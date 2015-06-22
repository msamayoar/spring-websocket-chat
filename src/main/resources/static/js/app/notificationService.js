/**
 * Created by Andrii on 20.06.2015.
 */
'use strict';
servicesModule.factory('NotificationService', ['toaster', function(toaster){
    return {
        error: function (message, title) {
            toaster.pop('error', title ? title : "", message);
        },
        warn: function (message, title) {
            toaster.pop('warning', title ? title : "", message);
        },
        success: function (message, title) {
            toaster.pop('success', title ? title : "", message);
        }
    };
}]);