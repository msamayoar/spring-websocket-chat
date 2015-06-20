/**
 * Created by Andrii on 20.06.2015.
 */
'use strict';
servicesModule.factory('AppEvents', ['$rootScope', function($rootScope){
    return {
        CHAT: {
            CONVERSATION: {
                CHANGED: "conversationChanged"
            },
            MESSAGES: {
                CHANGED: "messagesChanged"
            }
        },
        USER: {
            CHANGED: "userChanged"
        },
        CONTACTS: {
            CHANGED: "contactsChanged",
            SELECTED: "contactSelected"
        },
        fire: function (event) {
            $rootScope.$broadcast(event);
        }
    };
}]);