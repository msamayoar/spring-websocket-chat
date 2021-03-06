package com.tempest.moonlight.server.contacts.exceptions;

import com.tempest.moonlight.server.domain.contacts.ContactRequest;

/**
 * Created by Yurii on 2015-06-20.
 */
public class IllegalContactRequestResponseStatusException extends ContactRequestException {
    public IllegalContactRequestResponseStatusException(ContactRequest.Status responseStatus) {
        super("Illegal response status = " + responseStatus + ". Only [" + ContactRequest.Status.APPROVED + ", " + ContactRequest.Status.REJECTED + "] are allowed");
    }
}
