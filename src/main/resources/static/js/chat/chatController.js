'use strict';

controllersModule.controller('ChatController', ['$scope', '$location', '$interval', '$timeout', 'toaster', 'ChatSocket', 'AppEvents', 'ChatService', 'ContactsService', function ($scope, $location, $interval, $timeout, toaster, chatSocket, appEvents, chat, contacts) {

    var typing = undefined;

    $scope.sendTo = 'everyone';
    $scope.participants = [];
    $scope.newMessage = '';

    $scope.selectedContact = {};

    $scope.sendMessage = function () {
        chat.sendMessage(
            $scope.selectedContact.signature,
            "SNAFU",
            $scope.newMessage
        );
        $scope.newMessage = "";
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

    $scope.$on(appEvents.CONTACTS.SELECTED, function () {
        $timeout(function () {
            $scope.selectedContact = contacts.selected();
        })
    });

    $scope.$on(appEvents.CHAT.CONVERSATION.CHANGED, function () {
        $timeout(function () {
            $scope.participants = chat.participants;
        })
    });
}]);