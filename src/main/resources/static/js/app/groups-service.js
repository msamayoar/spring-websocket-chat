/**
 * Created by Yurii on 2015-06-22.
 */
'use strict';
servicesModule.factory('GroupsService', ['AppEvents', 'ChatSocket', function(appEvents, chatSocket) {


    var parseGroupParticipants = function(frame) {
        var groupWithParticipants = JSON.parse(frame.body);
        console.log(groupWithParticipants);
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

    var inviteAndKickParticipants = function(groupSignature, invite, kick) {
        chatSocket.send(
            paths.GROUPS.CHANGES_SEND,
            JSON.stringify(
                {
                    signature: groupSignature,
                    add: invite,
                    remove: kick
                }
            )
        )
    };

    return {
        initSubscription: function() {
            chatSocket.subscribe(
                paths.GROUP.GROUPS_SUB,
                function(frame) {
                    parseGroupParticipants(frame);
                }
            )
        },
        createGroup: function(groupSignature) {
            createGroup(groupSignature);
        },
        inviteAndKickParticipants: function(groupSignature, usersToInvite, usersToKick) {
            inviteAndKickParticipants(groupSignature, usersToInvite, usersToKick);
        }
    }
}]);