/**
 * Created by Andrii on 20.06.2015.
 */
'use strict';

controllersModule.controller('AppController', ['$scope', '$timeout', 'AppEvents', 'InitService', 'UserService', function ($scope, $timeout, appEvents, initService, userService) {
    initService.init();

    $scope.username = '';

    $scope.$on(appEvents.USER.CHANGED, function () {
        $timeout(function () {
            $scope.username = userService.user.username;
        })
    });
}]);