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
            CHANGED: "userChanged",
            FOUND: "contactsFound"
        },
        CONTACTS: {
            CHANGED: "contactsChanged",
            SELECTED: "contactSelected",
            GROUP: {
                CHANGED: "groupCreated"
            }
        },
        fire: function (event) {
            $rootScope.$broadcast(event);
        }
    };
}]);