package com.tempest.moonlight.server.controllers;

import com.tempest.moonlight.server.domain.User;
import com.tempest.moonlight.server.services.dto.DtoConverter;
import com.tempest.moonlight.server.services.dto.contacts.GenericParticipantDTO;
import com.tempest.moonlight.server.services.dto.users.UserDTO;
import com.tempest.moonlight.server.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.util.Collection;

/**
 * Created by Yurii on 2015-06-21.
 */
@Controller
public class UsersController {

    @Autowired
    private UserService userService;

    @Autowired
    private DtoConverter dtoConverter;

    @MessageMapping("users/match")
    @SendToUser("/queue/users/match")
    public Collection<UserDTO> onGetMatchingParticipants(@Payload GenericParticipantDTO participantDTO) {
        Collection<User> matching = userService.getMatching(participantDTO.getSignature());
        return (Collection<UserDTO>) dtoConverter.convertToDTOs(matching, UserDTO.class);
    }
}
