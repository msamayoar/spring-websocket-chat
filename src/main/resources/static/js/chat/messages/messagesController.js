/**
 * Created by Andrii on 21.06.2015.
 */
controllersModule.controller('MessagesController', ['$scope', '$timeout', 'AppEvents', 'ChatSocket', 'MessagesService', 'UserService', function ($scope, $timeout, appEvents, chatSocket, messagesService, userService) {
    $scope.messages = [];
    $scope.msgStatus = appConst.CHAT.MESSAGE.STATUS;

    $scope.currentUserName = userService.username();

    $scope.retrieveMessages = function () {
        log("Sending request to sync messages");
        chatSocket.send("/app/sync/messages");
    };

    $scope.$on(appEvents.CHAT.MESSAGES.CHANGED, function () {
        $timeout(function () {
            $scope.messages = messagesService.get();
        })
    });
}]);
