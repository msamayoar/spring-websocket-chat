/**
 * Created by Andrii on 20.06.2015.
 */
'use strict';

controllersModule.controller('ContactsController', ['$scope', '$timeout', 'AppEvents', 'ContactsService', 'SearchService', function ($scope, $timeout, appEvents, contactsService, searchService) {

    $scope.userContacts = [];
    $scope.selectedContact = {};

    $scope.searchContacts = function() {
        searchService.searchUsers("er");
        contactsService.sendInviteUserToContactsRequest("azaza");
    };

    $scope.selectContact = function (contact) {
        $scope.selectedContact = contact;
        contactsService.selectContact(contact);
    };

    $scope.$on(appEvents.CONTACTS.CHANGED, function () {
        $timeout(function () {
            $scope.userContacts = contactsService.get();
        })
    });

}]);