/**
 * Created by Yurii on 2015-06-22.
 */
'use strict';
servicesModule.factory('GroupsService', ['AppEvents', 'ChatSocket', function(appEvents, chatSocket) {

    var group = {};

    var parseGroupParticipants = function(frame) {
        group  = JSON.parse(frame.body);
        console.log(group);
    };

    var createGroup = function(groupSignature) {
        chatSocket.send(
            paths.GROUPS.CREATE_SEND,
            {},
            JSON.stringify(
                {
                    signature: groupSignature
                }
            )
        )
    };

    var getGroupParticipants = function(groupSignature) {
        chatSocket.send(
            paths.GROUPS.GET_PARTICIPANTS_SEND,
            {},
            JSON.stringify(
                {
                    signature: groupSignature
                }
            )
        );
    };

    var inviteAndKickParticipants = function(groupSignature, invite, kick) {
        //debugger;
        chatSocket.send(
            paths.GROUPS.CHANGES_SEND,
            {},
            JSON.stringify(
                {
                    signature: groupSignature,
                    add: invite,
                    remove: kick
                }
            )
        )
    };

    var notifyGroupCreated = function () {
        appEvents.fire(appEvents.CONTACTS.GROUP.CHANGED);
    };

    return {
        get: function () {
            return group;
        },
        createGroup: function(groupSignature) {
            createGroup(groupSignature);
        },
        getParticipants: function(groupSignature) {
            getGroupParticipants(groupSignature);
        },
        inviteAndKickParticipants: function(groupSignature, usersToInvite, usersToKick) {
            inviteAndKickParticipants(groupSignature, usersToInvite, usersToKick);
        },
        initSubscription: function() {
            chatSocket.subscribe(
                paths.GROUPS.GROUPS_SUB,
                function(frame) {
                    parseGroupParticipants(frame);
                    //chatService.setParticipants(group.participants);
                    notifyGroupCreated();
                }
            )
        }
    }
}]);