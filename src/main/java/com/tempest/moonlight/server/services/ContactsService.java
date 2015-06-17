package com.tempest.moonlight.server.services;

import com.tempest.moonlight.server.domain.GenericContact;

import java.util.Collection;

/**
 * Created by Yurii on 2015-06-18.
 */
public interface ContactsService {
    Collection<GenericContact> getContactsOfUser(String login);
    boolean addContact(GenericContact genericContact);
    boolean removeContact(GenericContact genericContact);
}
