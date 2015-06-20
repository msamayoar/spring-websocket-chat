/**
 * Created by Andrii on 20.06.2015.
 */
var paths = (function () {
    var APP = "/app/";
    var USER_QUEUE = "/user/queue/";

    var prfx = function(prefix, destination) {
        return prefix + destination;
    };

    var send_prfx = function(prefix, destination) {
        return prfx(prefix, destination);
    };

    var send = function(destination) {
        return send_prfx(APP, destination);
    };

    var sub_prfx = function(prefix, destination) {
        return prfx(prefix, destination);
    } ;

    var sub = function(destination) {
        return sub_prfx(USER_QUEUE, destination);
    };

    return {
        CHAT: {
            PRIVATE_SEND: send("chat/private"),
            INCOMING_SUB: sub("chat/incoming"),

            DELIVERY_SUB: sub("messages/delivery"),
            DELIVERY_SEND: send("messages/delivery")
        },
        SYNC: {
            PARTICIPANT_SEND: send("messages/participant"),
            PARTICIPANT_SUB: sub("messages/participant"),

            ALL_MESSAGES_SEND: send("messages/all"),
            ALL_MESSAGES_SUB: sub("messages/all"),

            OFFLINE_MESSAGES_SEND: send("messages/offline"),
            OFFLINE_MESSAGES_SUB: sub("messages/offline")
        },
        CONTACTS: {
            GET_SEND: send("contacts/get"),
            ALL_SUB: sub("contacts")
        },
        PRESENCE: {
            PRESENCE_SUB: sub("presence"),
            PRESENCE_SEND: send("presence")
        },
        ERRORS: {
            SUB: sub("errors")
        }
    };
}());
