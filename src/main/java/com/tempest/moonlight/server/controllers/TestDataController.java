package com.tempest.moonlight.server.controllers;

import com.tempest.moonlight.server.domain.ParticipantType;
import com.tempest.moonlight.server.domain.contacts.GenericContact;
import com.tempest.moonlight.server.domain.contacts.GenericParticipant;
import com.tempest.moonlight.server.services.contacts.ContactsService;
import com.tempest.moonlight.server.services.users.UserService;
import com.tempest.moonlight.server.util.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class TestDataController {

    @Autowired
    private UserService userService;

    @Autowired
    private ContactsService contactsService;

    private String user1Login = "user1";

    private Set<String> testUsers = new HashSet<>();

    {
        testUsers.add("user0");
        testUsers.add(user1Login);
        testUsers.add("user2");
        testUsers.add("user3");
        testUsers.add("user4");
        testUsers.add("user5");
        testUsers.add("user6");
        testUsers.add("user7");
        testUsers.add("user8");
        testUsers.add("user9");
        testUsers.add("a");
    }

    @PostConstruct
    public void addTestData() {
        testUsers.forEach(user -> userService.registerUser(user, user));

        List<GenericParticipant> participants = StreamUtils.convertToList(
                testUsers,
                userLogin -> new GenericParticipant(ParticipantType.USER, userLogin)
        );

        for (int i = 0; i < participants.size(); i++) {
            GenericParticipant participant = participants.get(i);
            for (int j = i + 1; j < participants.size(); j++) {
                contactsService.addContact(
                        new GenericContact(
                                participant,
                                participants.get(j)
                        )
                );
            }
        }
    }

}
