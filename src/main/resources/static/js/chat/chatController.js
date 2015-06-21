'use strict';

controllersModule.controller('ChatController', ['$scope', '$location', '$interval', '$timeout', 'ChatSocket', 'AppEvents', 'NotificationService', 'ChatService', 'ContactsService', function ($scope, $location, $interval, $timeout, chatSocket, appEvents, notification, chat, contacts) {

    var typing = undefined;

    $scope.sendTo = 'everyone';
    $scope.participants = [];
    $scope.newMessage = '';
    $scope.subject = '';
    $scope.subjectMaxLength = appConst.CHAT.SUBJECT_MAX_LENGTH;

    $scope.selectedContact = {
        signature: '',
        type: 0
    };

    $scope.sendMessage = function () {
        if($scope.newMessage){
            if(!$scope.selectedContact.signature) {
                notification.warn("Please, select somebody from the list of contacts to send a message.");
                return;
            }
            chat.sendMessage(
                $scope.selectedContact.signature,
                $scope.selectedContact.type,
                $scope.subject,
                $scope.newMessage
            );
        }
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

    var switchChatView = function (contact) {

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