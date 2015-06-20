/**
 * Created by Andrii on 20.06.2015.
 */
'use strict';
servicesModule.factory('AppEvents', ['$rootScope', function($rootScope){
    return {
        CONVERSATION_CHANGED: "conversationChanged",
        USER_CHANGED: "userChanged",
        CONTACTS_CHANGED: "contactsChanged",
        fire: function (event) {
            $rootScope.$broadcast(event);
        }
    };
}]);