package com.tempest.moonlight.server.controllers;

import com.tempest.moonlight.server.services.ContactsService;
import com.tempest.moonlight.server.services.dto.GenericContactDTO;
import com.tempest.moonlight.server.util.RandomStringUtil;
import com.tempest.moonlight.server.websockets.ToUserSender;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * Created by Yurii on 2015-06-16.
 */
@Controller
public class ContactsController {

    private static final Logger logger = Logger.getLogger(ContactsController.class.getName());

    private final Random RANDOM = new Random();

    @Autowired
    private ContactsService contactsService;

    @MessageMapping({ "/contacts", "/contacts/get" })
    @SendToUser("/queue/contacts/")
    public Collection<GenericContactDTO> getContacts(Principal principal) {
        /**
         * Mock implementation used!
         * TODO: change to valid implementation
         */
        logger.info("get user's contacts");
        int toReturn = 40 + RANDOM.nextInt(20);
        List<GenericContactDTO> dtoList = new ArrayList<>(toReturn);
        for (int i = 0; i < toReturn; i++) {
            dtoList.add(new GenericContactDTO(RANDOM.nextInt(2), RandomStringUtil.getRandomString(12)));
        }
//        toUserSender.sendToUserQueue(principal.getName(), "contacts/get", dtoList);
        return dtoList;
    }
}
