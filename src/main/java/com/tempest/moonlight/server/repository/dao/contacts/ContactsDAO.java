package com.tempest.moonlight.server.repository.dao.contacts;

import com.tempest.moonlight.server.domain.contacts.GenericContact;
import com.tempest.moonlight.server.repository.dao.DAO;

import java.util.Collection;

/**
 * Created by Yurii on 2015-06-18.
 */
public interface ContactsDAO extends DAO<GenericContact, GenericContact> {
    Collection<GenericContact> getContactsOfUser(String login);
}
