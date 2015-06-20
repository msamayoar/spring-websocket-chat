package com.tempest.moonlight.server.exceptions.contacts;

import com.tempest.moonlight.server.domain.contacts.ContactRequest;

/**
 * Created by Yurii on 2015-06-20.
 */
public class IncomingContactRequestNotFoundException extends ContactRequestException {
    public IncomingContactRequestNotFoundException(ContactRequest contactRequest) {
        super("Incoming contact request = " + contactRequest + " not found");
    }
}
