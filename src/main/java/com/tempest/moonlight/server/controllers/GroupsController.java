package com.tempest.moonlight.server.controllers;

import com.tempest.moonlight.server.domain.Group;
import com.tempest.moonlight.server.domain.ParticipantType;
import com.tempest.moonlight.server.domain.contacts.ContactRequest;
import com.tempest.moonlight.server.domain.contacts.GenericParticipant;
import com.tempest.moonlight.server.exceptions.contacts.ContactsException;
import com.tempest.moonlight.server.exceptions.groups.*;
import com.tempest.moonlight.server.services.dto.DtoConverter;
import com.tempest.moonlight.server.services.dto.contacts.ContactRequestDTO;
import com.tempest.moonlight.server.services.dto.contacts.GenericParticipantDTO;
import com.tempest.moonlight.server.services.dto.groups.GroupParticipantsChangeDTO;
import com.tempest.moonlight.server.services.dto.groups.GroupParticipantsDTO;
import com.tempest.moonlight.server.services.groups.GroupParticipantsChangesHolder;
import com.tempest.moonlight.server.services.groups.GroupService;
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
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Yurii on 2015-06-22.
 */
@Controller
public class GroupsController {

    private static final Logger logger = Logger.getLogger(GroupsController.class.getName());

    @Autowired
    private GroupService groupService;

    @Autowired
    private DtoConverter dtoConverter;

    @Autowired
    private ToParticipantSender toParticipantSender;

    @Autowired
    private ContactsController contactsController;

    @MessageMapping("groups/create")
    @SendToUser("/queue/groups")
    public GroupParticipantsDTO onCreateGroup(Principal principal, @Payload GenericParticipantDTO groupParticipantDto) throws GroupsException {
        String groupSignature = groupParticipantDto.getSignature();
        if(StringUtils.isEmpty(groupSignature)) {
            throw new InvalidGroupSignatureException();
        }
        Group group = checkGetGroup(groupSignature, false);
        groupService.addUserToGroup(group, principal.getName());
        return new GroupParticipantsDTO(
                group,
                (List<GenericParticipantDTO>) dtoConverter.convertToDTOs(groupService.getParticipants(group))
        );
    }

    @MessageMapping("groups/changes")
    @SendToUser("/queue/groups")
    public GroupParticipantsDTO onGroupParticipantsChange(Principal principal, @Payload GroupParticipantsChangeDTO participantsChangeDTO) throws GroupsException {
        String groupSignature = participantsChangeDTO.getSignature();
        if(StringUtils.isEmpty(groupSignature)) {
            throw new InvalidGroupSignatureException();
        }

        logger.info("onGroupParticipantsChange: participantsChangeDTO = " + participantsChangeDTO);

        Group group = checkGetGroup(groupSignature, true);
        if(!groupService.checkUserBelongsToGroup(group, principal.getName())) {
            throw new IllegalGroupAccessException(groupSignature);
        }

        GroupParticipantsChangesHolder changesHolder = groupService.processAddRemoveParticipants(
                group,
                dtoConverter.convertFromDTOs(participantsChangeDTO.getAdd()),
                dtoConverter.convertFromDTOs(participantsChangeDTO.getRemove())
        );

        logger.info("onGroupParticipantsChange: changesHolder = " + changesHolder);

        Collection<GenericParticipant> updatedParticipants = changesHolder.updatedParticipants;



        String sender = principal.getName();
        long millis = System.currentTimeMillis();

        Map<GenericParticipant, ContactRequestDTO> groupAddNotifications = changesHolder.addedParticipants.stream().collect(
                Collectors.toMap(
                        Function.identity(),
                        addedParticipant ->
                                new ContactRequestDTO(
                                        sender,
                                        addedParticipant.getSignature(),
                                        ParticipantType.GROUP,
                                        groupSignature,
                                        ContactRequest.Status.PENDING,
                                        millis
                                )
                )
        );

        Map<GenericParticipant, ContactRequestDTO> groupRemoveNotifications = changesHolder.removedParticipants.stream().collect(
                Collectors.toMap(
                        Function.identity(),
                        removedParticipant ->
                                new ContactRequestDTO(
                                        sender,
                                        removedParticipant.getSignature(),
                                        ParticipantType.GROUP,
                                        groupSignature,
                                        ContactRequest.Status.REJECTED,
                                        millis
                                )
                )
        );

        groupAddNotifications.forEach(
                (participant, contactRequestDTO) -> {
                    try {
                        contactsController.onContactRequest(
                                principal, contactRequestDTO
                        );
                    } catch (ContactsException e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        groupRemoveNotifications.forEach(
                (participant, contactRequestDTO) -> {
                    try {
                        contactsController.onContactRequest(
                                principal, contactRequestDTO
                        );
                    } catch (ContactsException e) {
                        throw new RuntimeException(e);
                    }
                }
        );

//        String destination = "contacts/reply";
//        toParticipantSender.sendToUsersQueue(
//                groupRemoveNotifications,
//                destination
//        );
//
//        toParticipantSender.sendToUsersQueue(
//                groupAddNotifications,
//                destination
//        );

        return new GroupParticipantsDTO(
                group,
                (List<GenericParticipantDTO>) dtoConverter.convertToDTOs(updatedParticipants)
        );
    }

    private Group checkGetGroup(String groupSignature, boolean requireExists) throws GroupsException {
        if(groupService.existsGroup(groupSignature) ^ requireExists) {
            if(requireExists) {
                throw new GroupNotExistsException(groupSignature);
            } else {
                throw new GroupAlreadyExistsException(groupSignature);
            }
        } else {
            if(requireExists) {
                return groupService.getGroup(groupSignature);
            } else {
                return groupService.createGroup(groupSignature);
            }
        }
    }

    @MessageExceptionHandler
    @SendToUser("/queue/errors")
    public String onGroupsException(GroupsException e) {
        return e.getMessage();
    }

}
