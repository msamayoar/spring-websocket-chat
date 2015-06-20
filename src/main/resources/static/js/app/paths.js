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
            DELIVERY_SUB: sub("messages/delivery"),
            DELIVERY_SEND: send("messages/delivery"),
            INCOMING_SUB: sub("chat/incoming")
        },
        CONTACTS: {
            GET_SEND: send("contacts/get"),
            ALL_SUB: sub("contacts")
        },
        PRESENCE: {
            PRESENCE_SUB: sub("presence"),
            PRESENCE_SEND: send("presence")
        }
    };
}());