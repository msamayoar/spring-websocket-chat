package com.tempest.moonlight.server.services;

import com.tempest.moonlight.server.domain.ParticipantType;
import com.tempest.moonlight.server.domain.contacts.ContactRequest;
import com.tempest.moonlight.server.domain.contacts.GenericContact;
import com.tempest.moonlight.server.domain.contacts.GenericParticipant;
import com.tempest.moonlight.server.exceptions.contacts.ContactRequestException;
import com.tempest.moonlight.server.exceptions.contacts.IncomingContactRequestNotFoundException;
import com.tempest.moonlight.server.exceptions.contacts.IllegalContactRequestResponseStatusException;
import com.tempest.moonlight.server.repository.dao.contacts.ContactRequestDAO;
import com.tempest.moonlight.server.repository.dao.contacts.ContactsDAO;
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

    @Autowired
    private ContactRequestDAO contactRequestDAO;

    @Override
    public Collection<GenericContact> getContactsOfUser(String login) {
        return contactsDAO.getContactsOfUser(login);
    }

    @Override
    public boolean processContactRequest(ContactRequest request) {
        GenericParticipant recipient = new GenericParticipant(ParticipantType.USER, request.getRecipient());
        GenericParticipant contact = request.getContact();

        GenericContact recipientContact = new GenericContact(recipient, contact);
        if(contactsDAO.exists(recipientContact)) {
            return false;
        }

        if(contactsDAO.exists(recipientContact.invert())) {
            return false;
        }

        request.setTime(System.currentTimeMillis());
        request.setStatus(ContactRequest.Status.PENDING);
        contactRequestDAO.save(request);
        return true;
    }

    @Override
    public boolean processContactRequestResponse(ContactRequest response) throws ContactRequestException {
        if(!contactRequestDAO.exists(response)) {
            throw new IncomingContactRequestNotFoundException(response);
        }
        ContactRequest.Status responseStatus = response.getStatus();
        if(!(responseStatus == ContactRequest.Status.APPROVED || responseStatus == ContactRequest.Status.REJECTED)) {
            throw new IllegalContactRequestResponseStatusException(responseStatus);
        }

        ContactRequest contactRequest = contactRequestDAO.get(response);

        if(responseStatus == ContactRequest.Status.APPROVED) {
            response.setTime(System.currentTimeMillis());
            contactRequestDAO.delete(contactRequest);
            return addContact(
                    new GenericContact(
                            ParticipantType.USER, contactRequest.getRecipient(),
                            contactRequest.getContact()
                    )
            );
        }

        return false;
    }

    @Override
    public boolean addContact(GenericContact contact) {
        GenericContact inverted = contact.invert();
        if(!(contactsDAO.exists(contact) || contactsDAO.exists(inverted))) {
            contactsDAO.save(contact);
            contactsDAO.save(inverted);
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
