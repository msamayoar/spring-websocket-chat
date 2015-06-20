'use strict';

controllersModule.controller('ChatController', ['$scope', '$location', '$interval', '$timeout', 'toaster', 'ChatSocket', 'AppEvents', 'ChatService', function ($scope, $location, $interval, $timeout, toaster, chatSocket, appEvents, chat) {

    function log(smth) {
        console.log(smth);
    }

    var typing = undefined;

    //$scope.username = '';
    $scope.sendTo = 'everyone';
    $scope.participants = [];
    $scope.messages = [];
    $scope.newMessage = '';

    $scope.retrieveMessages = function () {
        log("Sending request to sync messages");
        chatSocket.send("/app/sync/messages");
    };

    $scope.sendMessage = function () {
        chat.sendMessage(
            "user2",
            "SNAFU",
            $scope.newMessage
        )
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

    $scope.$on(appEvents.CONVERSATION_CHANGED, function () {
        $timeout(function () {
            $scope.participants = chat.participants;
            $scope.messages = chat.messages;
        })
    });
}]);