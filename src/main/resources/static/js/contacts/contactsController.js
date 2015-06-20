/**
 * Created by Andrii on 20.06.2015.
 */
'use strict';

controllersModule.controller('ContactsController', ['$scope', '$timeout', 'ChatSocket', 'ContactsService', 'AppEvents', function ($scope, $timeout, chatSocket, contacts, appEvents) {

    $scope.userContacts = [];
    $scope.selectedContact = "";

    $scope.selectContact = function (contact) {
        $scope.selectedContact = contact;
    };

    $scope.$on(appEvents.CONTACTS_CHANGED, function () {
        $timeout(function () {
            $scope.userContacts = contacts.get();
        })
    });

}]);