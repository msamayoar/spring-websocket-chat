/**
 * Created by Andrii on 20.06.2015.
 */
'use strict';

controllersModule.controller('ContactsController', ['$scope', '$timeout', 'AppEvents', 'NotificationService', 'ContactsService', 'SearchService', 'GroupsService', function ($scope, $timeout, appEvents, notification, contactsService, searchService, groupsService) {
    var userContactsCopy = [];

    $scope.userContacts = [];
    $scope.selectedContact = {};

    $scope.contactsFilter = "";
    $scope.filteredContacts = [];
    $scope.isGlobalContactsSearch = false;

    $scope.contactsLoaded = false;


    $scope.creatingNewGroup = false;
    $scope.newGroupSignature = "";
    $scope.beginCreatingNewGroup = function () {
        $scope.creatingNewGroup = true;
    };

    $scope.createNewGroup = function () {
        if($scope.newGroupSignature) {
            groupsService.createGroup($scope.newGroupSignature);
            $scope.creatingNewGroup = false;
        }
        return;
    };

    $scope.cancelNewGroupCreating = function () {
        $scope.creatingNewGroup = false;
        $scope.newGroupSignature = "";
    };

    var find = function (contacts, signature, type) {
        for (var i = 0; i < contacts.length; i++) {
            var obj = contacts[i];
            if(obj.signature === signature && obj.type === type) {
                return {
                    contact: obj,
                    index: i
                };
            }
        }
        return null;
    };

    $scope.searchGlobalContacts = function() {
        searchService.searchUsers($scope.contactsFilter);
    };

    $scope.cancelSearch = function () {
        $scope.contactsFilter = "";
        if($scope.isGlobalContactsSearch){
            $scope.isGlobalContactsSearch = false;
            $scope.userContacts = userContactsCopy;
            $scope.selectedContact = {};
            contactsService.reload();
        }
    };

    $scope.isUser = function (contact) {
        return contact.type === appConst.CONTACTS.TYPE.USER;
    };

    $scope.isGroup = function (contact) {
        return contact.type === appConst.CONTACTS.TYPE.GROUP;
    };
    
    $scope.addContact = function (contact) {
        contactsService.sendInviteUserToContactsRequest(contact.signature);
        var contactInTheList = find($scope.userContacts, contact.signature, contact.type);
        $scope.userContacts[contactInTheList.index].found = false;
        $scope.userContacts[contactInTheList.index].requestSent = true;
        notification.success("Add contact request successfully sent to " + contact.signature);
    };

    var highlightParticipants = function (participants) {
        for (var i = 0; i < $scope.userContacts.length; i++) {
            var obj = $scope.userContacts[i];
            $scope.userContacts[i].isParticipant = false;
            for (var j = 0; j < participants.length; j++) {
                var obj1 = participants[j];
                if(obj1.signature === obj.signature && obj1.type === obj.type){
                    $scope.userContacts[i].isParticipant = true;
                    break;
                }
            }
        }
    };

    var removeGroupProperties = function () {
        for (var i = 0; i < $scope.userContacts.length; i++) {
            $scope.userContacts[i].isParticipant = undefined;
            $scope.userContacts[i].participants = [];
        }
    };

    var groupChanged = function (group) {
        var existingGroup = find($scope.userContacts, group.signature, appConst.CONTACTS.TYPE.GROUP);
        if(existingGroup) {
            //$scope.userContacts[existingGroup.index] =
        } else {
            var contact = {
                signature: group.signature,
                type: appConst.CONTACTS.TYPE.GROUP,
                participants: group.participants
            };
            $scope.userContacts.push(contact);
            $scope.selectContact(contact);
        }
        highlightParticipants(group.participants);
    };

    $scope.selectContact = function (contact) {
        if($scope.isGlobalContactsSearch){
            var currentContact = find(userContactsCopy, contact.signature, contact.type);
            if(!currentContact) {
                return;
            }
        } else {
            var currentContact = find($scope.userContacts, contact.signature, contact.type);
            if(!currentContact) {
                return;
            }
        }
        if(contact.type === appConst.CONTACTS.TYPE.USER){
            removeGroupProperties();
        }
        $scope.selectedContact = contact;
        contactsService.selectContact(contact);
    };

    var prepareFoundContacts = function (contacts) {
        for (var i = 0; i < contacts.length; i++) {
            contacts[i].found = true;
        }
        return contacts;
    };

    $scope.$on(appEvents.USER.FOUND, function () {
        $timeout(function () {
            userContactsCopy = angular.copy($scope.userContacts);
            $scope.isGlobalContactsSearch = true;
            $scope.userContacts = prepareFoundContacts(searchService.getFound());
        })
    });

    $scope.$on(appEvents.CONTACTS.GROUP.CHANGED, function () {
        $timeout(function () {
            debugger;
            groupChanged(groupsService.get());
        })
    });

    $scope.$on(appEvents.CONTACTS.CHANGED, function () {
        $timeout(function () {
            $scope.userContacts = contactsService.get();
            $scope.contactsLoaded = true;
        })
    });

}]);