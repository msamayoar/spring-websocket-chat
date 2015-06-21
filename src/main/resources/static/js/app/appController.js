/**
 * Created by Andrii on 20.06.2015.
 */
'use strict';

controllersModule.controller('AppController', ['$scope', '$timeout', 'AppEvents', 'InitService', 'UserService', function ($scope, $timeout, appEvents, initService, userService) {
    var controllerScope = this;

    initService.init();

    this.username = "";

    $scope.$on(appEvents.USER.CHANGED, function () {
        $timeout(function () {
            controllerScope.username = userService.user.username;
        })
    });
}]);