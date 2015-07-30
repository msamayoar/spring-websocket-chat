package com.tempest.moonlight.server.contacts.controller;

import com.tempest.moonlight.server.contacts.exceptions.ContactRequestException;
import com.tempest.moonlight.server.contacts.exceptions.ContactsException;
import com.tempest.moonlight.server.contacts.exceptions.InvalidContactException;
import com.tempest.moonlight.server.contacts.exceptions.UnknownContactRequestRecipientException;
import com.tempest.moonlight.server.domain.ParticipantType;
import com.tempest.moonlight.server.domain.contacts.ContactRequest;
import com.tempest.moonlight.server.domain.contacts.GenericContact;
import com.tempest.moonlight.server.domain.contacts.GenericParticipant;
import com.tempest.moonlight.server.contacts.service.ContactsService;
import com.tempest.moonlight.server.contacts.dto.ContactRequestDTO;
import com.tempest.moonlight.server.common.dto.DtoConverter;
import com.tempest.moonlight.server.contacts.dto.GenericParticipantDTO;
import com.tempest.moonlight.server.util.StringUtils;
import com.tempest.moonlight.server.websockets.ToParticipantSender;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.*;

/**
 * Created by Yurii on 2015-06-16.
 */
@Controller
public class ContactsController {

    private static final Logger logger = Logger.getLogger(ContactsController.class.getName());

    @Autowired
    private ContactsService contactsService;

    @Autowired
    private ToParticipantSender toParticipantSender;

    @Autowired
    private DtoConverter dtoConverter;

    @MessageMapping({ "contacts", "contacts/get" })
    @SendToUser(value = "/queue/contacts", broadcast = false)
    public Collection<GenericParticipantDTO> getContacts(Principal principal) {
        return getContactsOfUser(principal.getName());
    }

    private Collection<GenericParticipantDTO> getContactsOfUser(String login) {
        Collection<GenericContact> contactsOfUser = contactsService.getContactsOfUser(login);
        Set<GenericParticipant> participants = new HashSet<>(ContactsService.asGenericParticipants(contactsOfUser));
        logger.info("Contacts of user = [login = " + login + ", contacts = " + participants);
        return (Collection<GenericParticipantDTO>) dtoConverter.convertToDTOs(participants);
    }

    @MessageMapping("contacts/request")
    public void onContactRequest(Principal principal, @Payload ContactRequestDTO contactRequestDTO) throws ContactsException {
        ContactRequest contactRequest = dtoConverter.convertFromDTO(contactRequestDTO, ContactRequest.class);
        logger.info("Processing contact request = " + contactRequest);

        if(StringUtils.isEmpty(contactRequest.getRecipient())) {
            throw new UnknownContactRequestRecipientException();
        }
        if(StringUtils.equalIgnoreCase(contactRequest.getRecipient(), principal.getName())) {
            throw new InvalidContactException();
        }
//        if(StringUtils.isEmpty(contactRequest.getContact().getSignature())) {
//            throw new UnknownContactRequestContactException();
//        }

        contactRequest.setInitiator(principal.getName());
        if(contactsService.processContactRequest(contactRequest)) {
            toParticipantSender.sendToUserQueue(contactRequest.getRecipient(), "contacts/request", dtoConverter.convertToDTO(contactRequest));
        }
    }

    @MessageMapping("contacts/reply")
    @SendToUser(value = "/queue/contacts/reply", broadcast = false)
    public ContactRequestDTO onContactRequestResponse(Principal principal, @Payload ContactRequestDTO contactRequestDTO) throws ContactRequestException {
        ContactRequest contactRequest = dtoConverter.convertFromDTO(contactRequestDTO, ContactRequest.class);

        contactRequest.setRecipient(principal.getName());

        logger.info("onContactRequestResponse: contactRequest = " + contactRequest);
        contactsService.processContactRequestResponse(contactRequest);

        ContactRequestDTO responseDTO = (ContactRequestDTO) dtoConverter.convertToDTO(contactRequest);
        GenericParticipant contact = contactRequest.getContact();
        if(contact.getType() == ParticipantType.USER) {
            toParticipantSender.sendToUserQueue(contact, "contacts/reply", responseDTO);
        }

        Map<GenericParticipant, Collection<GenericParticipantDTO>> usersContactsUpdated = new HashMap<>();
        contact.getSignature();
        usersContactsUpdated.put(contact, getContactsOfUser(contact.getSignature()));
        String recipient = contactRequest.getRecipient();
        usersContactsUpdated.put(
                new GenericParticipant(ParticipantType.USER, recipient),
                getContactsOfUser(recipient)
        );

        toParticipantSender.sendToUsersQueue(usersContactsUpdated, "contacts");

        return responseDTO;
    }

    @MessageExceptionHandler
    @SendToUser(value = "/queue/errors", broadcast = false)
    public String onContactException(ContactsException e) {
        logger.error("onContactException: ", e);
        return e.getMessage();
    }
}
