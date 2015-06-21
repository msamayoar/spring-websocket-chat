package com.tempest.moonlight.server.controllers;

import com.tempest.moonlight.server.services.dto.contacts.GenericContactDTO;
import com.tempest.moonlight.server.services.dto.contacts.GenericParticipantDTO;
import com.tempest.moonlight.server.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.util.Collection;

/**
 * Created by Yurii on 2015-06-21.
 */
@Controller
public class UsersController {

    @Autowired
    private UserService userService;

    @MessageMapping("users/match")
    public Collection<GenericParticipantDTO> onGetMatchingParticipants(@Payload GenericParticipantDTO participantDTO) {
        return null;
    }
}
