package com.tempest.moonlight.server.services.contacts;

import com.tempest.moonlight.server.domain.ParticipantType;
import com.tempest.moonlight.server.domain.contacts.ContactRequest;
import com.tempest.moonlight.server.domain.contacts.GenericContact;
import com.tempest.moonlight.server.domain.contacts.GenericParticipant;
import com.tempest.moonlight.server.exceptions.contacts.ContactRequestException;
import com.tempest.moonlight.server.exceptions.contacts.IncomingContactRequestNotFoundException;
import com.tempest.moonlight.server.exceptions.contacts.IllegalContactRequestResponseStatusException;
import com.tempest.moonlight.server.repository.dao.contacts.ContactRequestDAO;
import com.tempest.moonlight.server.repository.dao.contacts.ContactsDAO;
import com.tempest.moonlight.server.util.StringUtils;
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

        if(StringUtils.isEmpty(contact.getSignature())) {
            contact.setSignature(request.getInitiator());
            contact.setType(ParticipantType.USER);
        }

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
        if(!(response.isSystem() || contactRequestDAO.exists(response))) {
            throw new IncomingContactRequestNotFoundException(response);
        }
        ContactRequest.Status responseStatus = response.getStatus();
        if(!(responseStatus == ContactRequest.Status.APPROVED || responseStatus == ContactRequest.Status.REJECTED)) {
            throw new IllegalContactRequestResponseStatusException(responseStatus);
        }

        ContactRequest contactRequest = contactRequestDAO.get(response);
        GenericParticipant owner = new GenericParticipant(ParticipantType.USER, contactRequest.getRecipient());
        if(responseStatus == ContactRequest.Status.APPROVED) {
            response.setTime(System.currentTimeMillis());
            contactRequestDAO.delete(contactRequest);
            return addContact(
                    new GenericContact(
                            owner,
                            contactRequest.getContact()
                    )
            );
        } else {
            return removeContact(
                    new GenericContact(
                            owner,
                            contactRequest.getContact()
                    )
            );
        }
    }

    @Override
    public boolean addContact(GenericContact contact) {
        GenericContact inverted = contact.invert();
        if(!(contactsDAO.exists(contact) && contactsDAO.exists(inverted))) {
            contactsDAO.save(contact);
            contactsDAO.save(inverted);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeContact(GenericContact genericContact) {
        GenericContact inverted = genericContact.invert();
        if(contactsDAO.exists(genericContact) || contactsDAO.exists(inverted)) {
            contactsDAO.delete(genericContact);
            contactsDAO.delete(inverted);
            return true;
        }
        return false;
    }
}
