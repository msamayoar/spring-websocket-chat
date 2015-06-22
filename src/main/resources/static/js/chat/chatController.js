'use strict';

controllersModule.controller('ChatController', ['$scope', '$location', '$interval', '$timeout', 'ChatSocket', 'AppEvents', 'NotificationService', 'ChatService', 'ContactsService', 'GroupsService', function ($scope, $location, $interval, $timeout, chatSocket, appEvents, notification, chat, contacts, groupsService) {

    var typing = undefined;

    $scope.sendTo = 'everyone';
    $scope.newMessage = '';
    $scope.subject = '';
    $scope.subjectMaxLength = appConst.CHAT.SUBJECT_MAX_LENGTH;

    $scope.conversation = {
        name: "",
        isGroup: false,
        participants: [],
        contact: {}
    };

    $scope.group = {
        inviteUser: false,
        inviteUserUsername: ""
    };

    var clearInvite = function () {
        $scope.group.inviteUser = false;
        $scope.group.inviteUserUsername = "";
    };

    $scope.inviteUserAction = function () {
        //debugger;
        if($scope.group.inviteUser) {
            $scope.inviteUser();
            clearInvite();
        } else {
            $scope.group.inviteUser = true;
        }
    };

    $scope.inviteUser = function () {
        //debugger;
        if($scope.group.inviteUserUsername) {
            groupsService.inviteAndKickParticipants(
                $scope.conversation.contact.signature,
                [{
                    signature: $scope.group.inviteUserUsername,
                    type: appConst.CONTACTS.TYPE.USER
                }],
                []
            );
        }
        clearInvite();
    };

    $scope.sendMessage = function () {
        if($scope.newMessage){
            if(!$scope.conversation.contact.signature) {
                notification.warn("Please, select somebody from the list of contacts to send a message.");
                return;
            }
            chat.sendMessage(
                $scope.conversation.contact.signature,
                $scope.conversation.contact.type,
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

    var switchConversation = function (conversation) {
        $scope.conversation.contact = conversation.contact;
        switch(conversation.contact.type) {
            case appConst.CHAT.PARTICIPANT.TYPE.USER:
                $scope.conversation.name = conversation.contact.signature;
                $scope.conversation.isGroup = false;
                clearInvite();
                break;
            case appConst.CHAT.PARTICIPANT.TYPE.GROUP:
                $scope.conversation.name = conversation.contact.signature;
                $scope.conversation.isGroup = true;
                $scope.conversation.participants = conversation.participants;
                break;
        }

    };

    $scope.$on(appEvents.CHAT.CONVERSATION.CHANGED, function () {
        $timeout(function () {
            switchConversation(chat.conversation);
        })
    });
}]);