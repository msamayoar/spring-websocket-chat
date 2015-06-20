package com.tempest.moonlight.server.controllers;

import com.tempest.moonlight.server.domain.ParticipantType;
import com.tempest.moonlight.server.domain.contacts.GenericContact;
import com.tempest.moonlight.server.services.ContactsService;
import com.tempest.moonlight.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;

@Controller
public class TestDataController {

    @Autowired
    private UserService userService;

    @Autowired
    private ContactsService contactsService;

    @PostConstruct
    public void addTestData() {
        String user1 = "user1";
        userService.registerUser(user1, "1p");
        String user2 = "user2";
        userService.registerUser(user2, "2p");
        String user3 = "a";
        userService.registerUser(user3, "1");

        contactsService.addContact(
                new GenericContact(
                        ParticipantType.USER, user1, ParticipantType.USER,
                        user2
                )
        );
    }

}
