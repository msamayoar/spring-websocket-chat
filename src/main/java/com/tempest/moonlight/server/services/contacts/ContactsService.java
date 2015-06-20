package com.tempest.moonlight.server.services.contacts;

import com.tempest.moonlight.server.domain.contacts.ContactRequest;
import com.tempest.moonlight.server.domain.contacts.GenericContact;
import com.tempest.moonlight.server.exceptions.contacts.ContactRequestException;

import java.util.Collection;

/**
 * Created by Yurii on 2015-06-18.
 */
public interface ContactsService {
    Collection<GenericContact> getContactsOfUser(String login);

    boolean processContactRequest(ContactRequest contactRequest);
    boolean processContactRequestResponse(ContactRequest contactRequest) throws ContactRequestException;

    boolean addContact(GenericContact genericContact);
    boolean removeContact(GenericContact genericContact);
}
