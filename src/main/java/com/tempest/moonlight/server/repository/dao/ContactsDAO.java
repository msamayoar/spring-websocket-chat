package com.tempest.moonlight.server.repository.dao;

import com.tempest.moonlight.server.domain.GenericContact;

import java.util.Collection;

/**
 * Created by Yurii on 2015-06-18.
 */
public interface ContactsDAO extends DAO<GenericContact, GenericContact> {
    Collection<GenericContact> getContactsOfUser(String login);
}
