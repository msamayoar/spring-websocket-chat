package com.tempest.moonlight.server.services;

import com.tempest.moonlight.server.domain.GenericContact;
import com.tempest.moonlight.server.repository.dao.ContactsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by Yurii on 2015-06-18.
 */
@Service
public class ContactsServiceImpl implements ContactsService {

    @Autowired
    private ContactsDAO contactsDAO;

    @Override
    public Collection<GenericContact> getContactsOfUser(String login) {
        return contactsDAO.getContactsOfUser(login);
    }

    @Override
    public boolean addContact(GenericContact genericContact) {
        if(!contactsDAO.exists(genericContact)) {
            contactsDAO.save(genericContact);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeContact(GenericContact genericContact) {
        if(contactsDAO.exists(genericContact)) {
            contactsDAO.delete(genericContact);
            return true;
        }
        return false;
    }
}
