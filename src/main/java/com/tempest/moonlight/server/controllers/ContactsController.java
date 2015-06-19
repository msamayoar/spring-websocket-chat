package com.tempest.moonlight.server.controllers;

import com.tempest.moonlight.server.domain.ContactType;
import com.tempest.moonlight.server.domain.GenericContact;
import com.tempest.moonlight.server.services.ContactsService;
import com.tempest.moonlight.server.services.dto.DtoConverter;
import com.tempest.moonlight.server.services.dto.GenericContactDTO;
import com.tempest.moonlight.server.util.RandomStringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final Random RANDOM = new Random();

    @Autowired
    private ContactsService contactsService;

    @Autowired
    private DtoConverter dtoConverter;

    @MessageMapping({ "/contacts", "/contacts/get" })
    @SendToUser("/queue/contacts/")
    public Collection<GenericContactDTO> getContacts(Principal principal) {
        /**
         * Mock implementation used!
         * TODO: change to valid implementation
         */
        logger.info("get user's contacts");
        int toReturn = 40 + RANDOM.nextInt(20);
        Collection<GenericContact> contacts = new ArrayList<>(toReturn);
        for (int i = 0; i < toReturn; i++) {
            contacts.add(
                    new GenericContact(
                            ContactType.getByValue(RANDOM.nextInt(2)),
                            RandomStringUtil.getRandomString(12),
                            ContactType.getByValue(RANDOM.nextInt(2)),
                            RandomStringUtil.getRandomString(12)
                    )
            );
        }

        return (Collection<GenericContactDTO>) dtoConverter.convertToOutgoing(contacts);
    }
}
