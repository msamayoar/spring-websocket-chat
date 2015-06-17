'use strict';

/* Controllers */

angular.module('springChat.controllers', ['toaster'])
    .controller('ChatController', ['$scope', '$location', '$interval', 'toaster', 'ChatSocket', function ($scope, $location, $interval, toaster, chatSocket) {

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

        var initStompClient = function () {
            chatSocket.init('/ws');

            chatSocket.connect(function (frame) {

                $scope.username = frame.headers['user-name'];

                chatSocket.subscribe("/app/chat.participants", function (message) {
                    $scope.participants = JSON.parse(message.body);
                });

                chatSocket.subscribe("/topic/chat.login", function (message) {
                    console.log("in subscribe callback on /topic/chat.login");
                    $scope.participants.unshift({username: JSON.parse(message.body).username, typing: false});
                });

                chatSocket.subscribe("/topic/chat.logout", function (message) {
                    var username = JSON.parse(message.body).username;
                    for (var index in $scope.participants) {
                        if ($scope.participants[index].username == username) {
                            $scope.participants.splice(index, 1);
                        }
                    }
                });

                chatSocket.subscribe("/topic/chat.typing", function (message) {
                    var parsed = JSON.parse(message.body);
                    if (parsed.username == $scope.username) return;

                    for (var index in $scope.participants) {
                        var participant = $scope.participants[index];

                        if (participant.username == parsed.username) {
                            $scope.participants[index].typing = parsed.typing;
                        }
                    }
                });

                chatSocket.subscribe("/topic/presence", function (message) {
                    var presence = JSON.parse(message.body);
                    console.log("Presence '" + presence.status + "' received from " + presence.user);
                });

                chatSocket.subscribe("/topic/chat.message", function (message) {
                    $scope.messages.unshift(JSON.parse(message.body));
                });

                chatSocket.subscribe("/user/queue/chat.message", function (message) {
                    var parsed = JSON.parse(message.body);
                    parsed.priv = true;
                    $scope.messages.unshift(parsed);
                });

                chatSocket.subscribe("/user/queue/errors", function (message) {
                    toaster.pop('error', "Error", message.body);
                });

                chatSocket.subscribe("/user/queue/sync.messages", function (message) {
                    var messages = JSON.parse(message.body);

                    log("sync result = " + messages);

                    var length = messages.length;
                    for (var i = 0; i < length; i++) {
                        log(messages[i]);
                    }

                    for (i = messages.length; i--;) {
                        log("! " + messages[i]);
                    }
                });

            }, function (error) {
                toaster.pop('error', 'Error', 'Connection error ' + error);

            });
        };

        initStompClient();
    }]);
