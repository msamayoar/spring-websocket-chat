package com.tempest.moonlight.server.controllers;

import com.tempest.moonlight.server.domain.contacts.ContactRequest;
import com.tempest.moonlight.server.domain.contacts.GenericContact;
import com.tempest.moonlight.server.exceptions.contacts.ContactRequestException;
import com.tempest.moonlight.server.exceptions.local.dto.DtoException;
import com.tempest.moonlight.server.services.ContactsService;
import com.tempest.moonlight.server.services.dto.ContactRequestDTO;
import com.tempest.moonlight.server.services.dto.DtoConverter;
import com.tempest.moonlight.server.services.dto.GenericContactDTO;
import com.tempest.moonlight.server.websockets.ToUserSender;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
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

//    private final Random RANDOM = new Random();

    @Autowired
    private ContactsService contactsService;

    @Autowired
    private ToUserSender toUserSender;

    @Autowired
    private DtoConverter dtoConverter;

    @MessageMapping({ "/contacts", "/contacts/get" })
    @SendToUser(value = "/queue/contacts/", broadcast = false)
    public Collection<GenericContactDTO> getContacts(Principal principal) {
//        /**
//         * Mock implementation used!
//         * TODO: change to valid implementation
//         */
//        logger.info("get user's contacts");
//        int toReturn = 40 + RANDOM.nextInt(20);
//        Collection<GenericContact> contacts = new ArrayList<>(toReturn);
//        for (int i = 0; i < toReturn; i++) {
//            contacts.add(
//                    new GenericContact(
//                            ParticipantType.getByValue(RANDOM.nextInt(2)),
//                            RandomStringUtil.getRandomString(12),
//                            ParticipantType.getByValue(RANDOM.nextInt(2)),
//                            RandomStringUtil.getRandomString(12)
//                    )
//            );
//        }
        Collection<GenericContact> contacts = contactsService.getContactsOfUser(principal.getName());
        return (Collection<GenericContactDTO>) dtoConverter.convertToDTOs(contacts);
    }

    @MessageMapping("/contacts/add")
    public void onContactRequest(Principal principal, ContactRequestDTO contactRequestDTO) throws DtoException {
        ContactRequest contactRequest = dtoConverter.convertFromDTO(contactRequestDTO, ContactRequest.class);
        logger.info("Processing contact request = " + contactRequest);

        contactRequest.setInitiator(principal.getName());
        if(contactsService.processContactRequest(contactRequest)) {
            toUserSender.sendToUserQueue(contactRequest.getRecipient(), "contacts/requests", contactRequestDTO);
        }
    }

    @MessageMapping("/contacts/reply")
    @SendToUser(value = "/queue/contacts/response", broadcast = false)
    public ContactRequestDTO onContactRequestResponse(Principal principal, ContactRequestDTO contactRequestDTO) throws DtoException, ContactRequestException {
        ContactRequest contactRequest = dtoConverter.convertFromDTO(contactRequestDTO, ContactRequest.class);
        contactRequest.setRecipient(principal.getName());
        if(contactsService.processContactRequestResponse(contactRequest)) {
            ContactRequestDTO responseDTO = (ContactRequestDTO) dtoConverter.convertToDTO(contactRequest, ContactRequestDTO.class);
            toUserSender.sendToUserQueue(contactRequest.getContact().getSignature(), "contacts/response", responseDTO);
            return responseDTO;
        } else {
            return null;//TODO
        }
    }

    @MessageExceptionHandler
    @SendToUser(value = "/queue/errors", broadcast = false)
    public String onContactRequestException(ContactRequestException e) {
        return e.getMessage();
    }
}
