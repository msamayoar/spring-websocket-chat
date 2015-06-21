/**
 * Created by Andrii Mozharovskyi on 18.05.2015.
 */

'use strict';

/* Login Module */

var springChatLogin = angular.module('springChatLogin', []);

springChatLogin.factory('chatConst', ['$location', function($location) {
    var data = {
        serverUrl: "http://" + $location.host() + ":" + $location.port()
    };

    return data;
}]);

springChatLogin.controller('RegistrationController', ['$scope', '$http', '$location', 'chatConst', function ($scope, $http, $location, chatConst) {

    $scope.messages = {
        registrationInfo: "Enter a login and password to sign up.",
        loginInfo: "Enter a nickname to sign in.",
        registrationFailed: "",
        registrationSuccessful: ""
    };

    $scope.flags = {
        registrationFailed: false,
        registrationSuccessful: false,
        isRegistrationForm: false
    };

    $scope.user = {
        username: "",
        password: "",
        passwordConfirmation: ""
    };

    $scope.showRegistrationForm = function () {
        $scope.flags.isRegistrationForm = true;
        $scope.user.username = "";
        $scope.user.password = "";
        $scope.user.passwordConfirmation = "";
    };

    $scope.register = function () {
        $scope.flags.registrationFailed = false;
        $scope.flags.registrationSuccessful = false;
        $http({
            method: 'POST',
            url: chatConst.serverUrl + "/register",
            headers: {
                "Accept" : "text/plain"
            },
            data: {
                login: $scope.user.username,
                password: $scope.user.password,
                passwordConfirmation: $scope.user.passwordConfirmation
            }
        }).success(function(data, status, headers, config) {
            $scope.messages.registrationSuccessful = "Registration successful! Please, login with your credentials.";
            $scope.flags.registrationSuccessful = true;
            $scope.flags.isRegistrationForm = false;
        }).error(function(data, status, headers, config) {
            $scope.messages.registrationFailed = status;
            $scope.flags.registrationFailed = true;
        });
    };
}]);

springChatLogin.directive("compareTo", function() {
    return {
        require: "ngModel",
        scope: {
            otherModelValue: "=compareTo"
        },
        link: function(scope, element, attributes, ngModel) {

            ngModel.$validators.compareTo = function(modelValue) {
                return modelValue == scope.otherModelValue;
            };

            scope.$watch("otherModelValue", function() {
                ngModel.$validate();
            });
        }
    };
});