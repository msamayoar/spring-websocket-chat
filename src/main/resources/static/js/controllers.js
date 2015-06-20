'use strict';

/* Controllers */

var controllersModule = angular.module('springChat.controllers', ['toaster']);

controllersModule.controller('ChatController', ['$scope', '$location', '$interval', '$timeout', 'toaster', 'ChatSocket', 'EventConst', 'ChatService', 'SocketService', function ($scope, $location, $interval, $timeout, toaster, chatSocket, eventConst, chat, socketService) {

    socketService.init();

    function log(smth) {
        console.log(smth);
    }

    var typing = undefined;

    $scope.username = '';
    $scope.sendTo = 'everyone';
    $scope.participants = [];
    $scope.messages = [];
    $scope.newMessage = '';

    $scope.retrieveMessages = function () {
        log("Sending request to sync messages");
        chatSocket.send("/app/sync/messages");
    };

    $scope.sendMessage = function () {
        var destination = "/app/chat.message";

        if ($scope.sendTo != "everyone") {
            destination = "/app/chat.private." + $scope.sendTo;
            $scope.messages.unshift({text: $scope.newMessage, from: 'you', priv: true, to: $scope.sendTo});
        }

        chatSocket.send(destination, {}, JSON.stringify({text: $scope.newMessage}));
        $scope.newMessage = '';
    };

    $scope.startTyping = function () {
        // Don't send notification if we are still typing or we are typing a private message
        if (angular.isDefined(typing) || $scope.sendTo != "everyone") return;

        typing = $interval(function () {
            $scope.stopTyping();
        }, 500);

        chatSocket.send("/topic/chat.typing", {}, JSON.stringify({username: $scope.username, typing: true}));
    };

    $scope.stopTyping = function () {
        if (angular.isDefined(typing)) {
            $interval.cancel(typing);
            typing = undefined;

            chatSocket.send("/topic/chat.typing", {}, JSON.stringify({username: $scope.username, typing: false}));
        }
    };

    $scope.privateSending = function (username) {
        $scope.sendTo = (username != $scope.sendTo) ? username : 'everyone';
    };

    $scope.$on(eventConst.USER_CHANGED, function () {
        $timeout(function () {
            $scope.username = chat.user.username;
        })
    });

    $scope.$on(eventConst.CONVERSATION_CHANGED, function () {
        $timeout(function () {
            $scope.participants = chat.participants;
            $scope.messages = chat.messages;
        })
    });
}]);

controllersModule.controller('ContactsController', ['$scope', '$timeout', 'ChatSocket', 'ContactsService', 'EventConst', function ($scope, $timeout, chatSocket, contacts, eventConst) {

    $scope.userContacts = [];
    $scope.selectedContact = 0;

    $scope.selectContact = function (contact) {
        debugger;
        $scope.selectedContact = contact;
    };

    $scope.$on(eventConst.CONTACTS_CHANGED, function () {
        $timeout(function () {
            $scope.userContacts = contacts.get();
        })
    });

}]);


