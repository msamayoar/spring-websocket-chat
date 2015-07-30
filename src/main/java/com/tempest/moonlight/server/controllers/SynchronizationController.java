package com.tempest.moonlight.server.controllers;

import com.tempest.moonlight.server.domain.ParticipantType;
import com.tempest.moonlight.server.domain.contacts.GenericParticipant;
import com.tempest.moonlight.server.domain.messages.ChatMessage;
import com.tempest.moonlight.server.contacts.exceptions.ContactsException;
import com.tempest.moonlight.server.contacts.exceptions.InvalidContactException;
import com.tempest.moonlight.server.groups.exceptions.GroupsException;
import com.tempest.moonlight.server.groups.exceptions.IllegalGroupAccessException;
import com.tempest.moonlight.server.common.dto.DtoConverter;
import com.tempest.moonlight.server.contacts.dto.GenericParticipantDTO;
import com.tempest.moonlight.server.messages.dto.ChatMessageDTO;
import com.tempest.moonlight.server.groups.service.GroupService;
import com.tempest.moonlight.server.messages.services.MessageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Collection;

@Controller
public class SynchronizationController {

    private static final Logger logger = Logger.getLogger(SynchronizationController.class.getName());

//    @Autowired
//    private ToParticipantSender toParticipantSender;

    @Autowired
    private DtoConverter dtoConverter;

    @Autowired
    private MessageService messageService;

    @Autowired
    private GroupService groupService;

    @MessageMapping("messages/all")
    @SendToUser(value = "messages/all", broadcast = false)
    public Collection<ChatMessageDTO> onGetAllMessagesRequest(Principal principal) {
        String name = principal.getName();
        Collection<ChatMessage> messagesOfUser = messageService.getMessagesOfUser(name);
//        toParticipantSender.sendToUserQueue(name, "sync/messages", messagesOfUser);
        return (Collection<ChatMessageDTO>) dtoConverter.convertToDTOs(messagesOfUser);
    }

    /*
    @MessageMapping("messages/participant")
    @SendToUser(value = "/queue/messages/participant", broadcast = false)
//    @SendToUser(value = "messages/participant", broadcast = false)
    public Collection<ChatMessageDTO> onGetMessagesWithContactRequest(Principal principal, @Payload GenericContactDTO contactDTO) throws ContactsException {
        GenericParticipant contact = dtoConverter.convertFromDTO(contactDTO).getContact();
        if(contact == null) {
            throw new InvalidContactException();
        }
        if(contact.getType() == null) {
            contact.setType(ParticipantType.USER);
        }
        String name = principal.getName();
//        logger.error("onGetMessagesWithContactRequest: messages = " + messagesBetween);
        return (Collection<ChatMessageDTO>) dtoConverter.convertToDTOs(messageService.getMessagesBetween(name, contact));
    }
    */

    @MessageMapping("messages/participant")
    @SendToUser(value = "/queue/messages/participant", broadcast = false)
//    @SendToUser(value = "messages/participant", broadcast = false)
    public Collection<ChatMessageDTO> onGetMessagesWithContactRequest(Principal principal, @Payload GenericParticipantDTO participantDTO) throws ContactsException, IllegalGroupAccessException {
        GenericParticipant contact = dtoConverter.convertFromDTO(participantDTO);
        if(contact == null) {
            throw new InvalidContactException();
        }
        if(contact.getType() == null) {
            contact.setType(ParticipantType.USER);
        }
        String name = principal.getName();

        if(contact.getType() == ParticipantType.GROUP) {
            if(!groupService.checkUserBelongsToGroup(contact.getSignature(), name)) {
                throw new IllegalGroupAccessException(contact.getSignature());
            }
        }

//        logger.error("onGetMessagesWithContactRequest: messages = " + messagesBetween);
        return (Collection<ChatMessageDTO>) dtoConverter.convertToDTOs(messageService.getMessagesBetween(name, contact));
    }

    @MessageMapping("messages/offline")
    @SendToUser(value = "messages/offline", broadcast = false)
    public Collection<ChatMessageDTO> onGetOfflineMessagesRequest(Principal principal) {
        return null;
    }

    @SendToUser(value = "/queue/errors", broadcast = false)
    public String onMessageHandlingException(ContactsException e) {
        return e.getMessage();
    }

    @SendToUser(value = "/queue/errors", broadcast = false)
    public String onGroupsException(GroupsException e) {
        return e.getMessage();
    }
}
