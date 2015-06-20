package com.tempest.moonlight.server.exceptions.contacts;

import com.tempest.moonlight.server.domain.contacts.GenericParticipant;

/**
 * Created by Yurii on 2015-06-21.
 */
public class InvalidContactException extends ContactsException {

    public InvalidContactException() {
        super("Invalid contact");
    }

    public InvalidContactException(GenericParticipant contact) {
        super("Invalid contact = [type = "+ contact.getType() +", signature = "+ contact.getSignature() +"]");
    }
}
