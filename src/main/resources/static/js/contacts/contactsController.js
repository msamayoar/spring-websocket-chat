/**
 * Created by Andrii on 20.06.2015.
 */
'use strict';

controllersModule.controller('ContactsController', ['$scope', '$timeout', 'ContactsService', 'AppEvents', function ($scope, $timeout, contacts, appEvents) {

    $scope.userContacts = [];
    $scope.selectedContact = {};

    $scope.selectContact = function (contact) {
        $scope.selectedContact = contact;
        contacts.selectContact(contact);
    };

    $scope.$on(appEvents.CONTACTS.CHANGED, function () {
        $timeout(function () {
            $scope.userContacts = contacts.get();
        })
    });

}]);