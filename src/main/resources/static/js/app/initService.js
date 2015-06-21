/**
 * Created by Andrii on 20.06.2015.
 */
'use strict';
servicesModule.factory('InitService',
    ['ChatSocket', 'NotificationService', 'ChatService', 'ContactsService', 'UserService', 'MessagesService',  'SearchService', function(chatSocket, notification, chat, contacts, userService, messages, searchService) {

    var initStompClient = function () {
        chatSocket.init('/ws');

        chatSocket.connect(function (frame) {
            userService.user.username = frame.headers['user-name'];
            userService.notifyUpdated();

            chat.initSubscription();
            messages.initSubscription();

            contacts.initSubscription();
            contacts.initData();

            searchService.initSubscription();
        }, function (error) {
            notification.error('Connection error ' + error);
        });
    };

    return {
        init: function () {
            initStompClient();
        }
    }
}]);