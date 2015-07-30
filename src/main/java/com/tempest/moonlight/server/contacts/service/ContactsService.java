package com.tempest.moonlight.server.contacts.service;

import com.tempest.moonlight.server.domain.contacts.ContactRequest;
import com.tempest.moonlight.server.domain.contacts.GenericContact;
import com.tempest.moonlight.server.domain.contacts.GenericParticipant;
import com.tempest.moonlight.server.contacts.exceptions.ContactRequestException;
import com.tempest.moonlight.server.util.CollectionsUtils;

import java.util.Collection;

/**
 * Created by Yurii on 2015-06-18.
 */
public interface ContactsService {
    Collection<GenericContact> getContactsOfUser(String login);

    static Collection<GenericParticipant> asGenericParticipants(Collection<GenericContact> contacts) {
        return CollectionsUtils.convertToList(contacts, GenericContact::getContact);
    }

    boolean processContactRequest(ContactRequest contactRequest);
    boolean processContactRequestResponse(ContactRequest contactRequest) throws ContactRequestException;

    boolean addContact(GenericContact genericContact);
    boolean removeContact(GenericContact genericContact);
}
