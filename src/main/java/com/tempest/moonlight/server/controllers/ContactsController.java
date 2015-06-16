package com.tempest.moonlight.server.controllers;

import com.tempest.moonlight.server.services.dto.GenericContactDTO;
import com.tempest.moonlight.server.util.RandomStringUtil;
import com.tempest.moonlight.server.websockets.ToUserSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Yurii on 2015-06-16.
 */
@Controller
public class ContactsController {

    private final Random RANDOM = new Random();

//    @Autowired
//    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private ToUserSender toUserSender;

    @MessageMapping("/contacts/get")
    @SendToUser
    public void getContacts(Principal principal) {
        /**
         * Mock implementation used!
         * TODO: change to valid implementation
         */
        int toReturn = 40 + RANDOM.nextInt(20);
        List<GenericContactDTO> dtoList = new ArrayList<>(toReturn);
        for (int i = 0; i < toReturn; i++) {
            dtoList.add(new GenericContactDTO(RANDOM.nextInt(2), RandomStringUtil.getRandomString(12)));
        }
        toUserSender.sendToUserQueue(principal.getName(), "contacts/get", dtoList);
    }
}
