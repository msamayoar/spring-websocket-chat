package com.tempest.moonlight.server.users.controllers;

import com.tempest.moonlight.server.domain.contacts.GenericParticipant;
import com.tempest.moonlight.server.common.dto.DtoConverter;
import com.tempest.moonlight.server.contacts.dto.GenericParticipantDTO;
import com.tempest.moonlight.server.users.service.UserService;
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
    public Collection<GenericParticipantDTO> onGetMatchingParticipants(@Payload GenericParticipantDTO participantDTO) {
        Collection<GenericParticipant> matching = userService.getMatching(participantDTO.getSignature());
        return (Collection<GenericParticipantDTO>) dtoConverter.convertToDTOs(matching);
    }
}
