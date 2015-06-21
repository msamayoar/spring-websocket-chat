/**
 * Created by Andrii on 21.06.2015.
 */
var appConst = (function () {
    return {
        CHAT: {
            SUBJECT_MAX_LENGTH: 140,
            PARTICIPANT:{
                TYPE:{
                    USER: 0,
                    GROUP: 1
                }
            },
            MESSAGE: {
                STATUS: {
                    PENDING: -1,
                    ARRIVED: 0,
                    DELIVERED: 1,
                    READ: 2
                }
            }
        },
        CONTACTS: {
            TYPE: {
                USER: 0,
                GROUP: 1
            },
            REQUEST: {
                STATUS: {
                    PENDING: 0,
                    APPROVED: 1,
                    REJECTED: 2,
                    REVOKED: 3
                }
            }
        }
    }
}());