package com.tempest.moonlight.server.controllers;

import java.security.Principal;
import java.util.Collection;

import com.tempest.moonlight.server.domain.ParticipantType;
import com.tempest.moonlight.server.domain.contacts.GenericParticipant;
import com.tempest.moonlight.server.domain.messages.MessageDeliveryStatus;
import com.tempest.moonlight.server.domain.messages.MessageStatus;
import com.tempest.moonlight.server.exceptions.chat.*;
import com.tempest.moonlight.server.repository.dao.ActiveUsersDAO;
import com.tempest.moonlight.server.services.messages.MessageService;
import com.tempest.moonlight.server.services.users.UserService;
import com.tempest.moonlight.server.services.dto.DtoConverter;
import com.tempest.moonlight.server.services.dto.messages.ChatMessageDTO;
import com.tempest.moonlight.server.util.StringUtils;
import com.tempest.moonlight.server.websockets.CustomMessageHeadersAccessor;
import com.tempest.moonlight.server.websockets.ToParticipantSender;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.config.WebSocketMessageBrokerStats;

import com.tempest.moonlight.server.domain.messages.ChatMessage;
//import com.tempest.moonlight.server.domain.SessionProfanity;
//import com.tempest.moonlight.server.util.ProfanityChecker;

@Controller
public class ChatController {
	private static final Logger logger = Logger.getLogger(ChatController.class.getName());

	@Autowired
    private WebSocketMessageBrokerStats stats;

	@Autowired
    private ActiveUsersDAO activeUsersDAO;

    @Autowired
    private ToParticipantSender toParticipantSender;

    @Autowired
    private DtoConverter dtoConverter;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    /*
	@SubscribeMapping("/chat.participants")
	public Collection<String> retrieveParticipants(Principal principal) {
        Collection<String> allActiveUsers = activeUsersDAO.getActiveUsers();
        allActiveUsers.remove(principal.getName());
        return allActiveUsers;
    }
    */

    @MessageMapping("/chat/private")
    public void onUserPrivate(Principal sender, Message message, @Payload ChatMessageDTO chatMessageDTO) throws MessageHandlingException {
        processUserPrivateMessage(sender, message, chatMessageDTO);
    }

    @MessageMapping("/chat/private/{login}")
    public void onUserPrivate(Principal sender, Message message, @Payload ChatMessageDTO chatMessageDTO, @DestinationVariable("login") String login) throws MessageHandlingException {
        chatMessageDTO.setRecipient(login);
        processUserPrivateMessage(sender, message, chatMessageDTO);
    }

    private void processUserPrivateMessage(Principal sender, Message message, ChatMessageDTO chatMessageDTO) throws MessageHandlingException {
        ChatMessage chatMessage = dtoConverter.convertFromDTO(chatMessageDTO, ChatMessage.class);
        chatMessage.setFrom(sender.getName());

        chatMessage = onUserPrivateMessageInner(chatMessage, message);

        sendDeliveryStatus(chatMessage.getFrom(), new MessageDeliveryStatus(chatMessage, MessageStatus.ARRIVED));
        toParticipantSender.sendToUserQueue(
                chatMessage.getRecipient().getSignature(),
                "chat/incoming",
                dtoConverter.convertToDTO(chatMessage, ChatMessageDTO.class)
        );
    }

    private ChatMessage onUserPrivateMessageInner(ChatMessage chatMessage, Message message) throws MessageHandlingException {
        GenericParticipant recipient = checkSetUpMessage(chatMessage, message).getRecipient();

        String recipientSignature = recipient.getSignature();
        if(!userService.checkUserExists(recipientSignature)) {
            throw new UserDoesNotExistException(recipientSignature);
        }

        logger.info("user sent message = " + chatMessage);

        messageService.saveMessage(chatMessage);
        return chatMessage;
    }

    public static ChatMessage checkSetUpMessage(ChatMessage chatMessage, Message message) throws MessageHandlingException {
        if(StringUtils.isEmpty(chatMessage.getPacketId())) {
            throw new EmptyMessagePacketIdException();
        }

        GenericParticipant recipient = chatMessage.getRecipient();
        if(recipient.getType() == null) {
            chatMessage.setType(ParticipantType.USER);
        } else if(recipient.getType() != ParticipantType.USER) {
            throw new IllegalMessageRecipientType(recipient.getType());
        }

        String recipientSignature = recipient.getSignature();
        if(!StringUtils.hasText(recipientSignature)) {
            throw new InvalidUserLoginException(recipientSignature);
        }

        CustomMessageHeadersAccessor headerAccessor = CustomMessageHeadersAccessor.wrap(message);
        headerAccessor.setImmutable();

        chatMessage.setTime(headerAccessor.getTimestamp());
        chatMessage.setUuid(headerAccessor.getId().toString());

        return chatMessage;
    }

    @MessageMapping("messages/delivery")
    public void onMessagedeliveryStatus(Principal principal, MessageDeliveryStatus deliveryStatus) throws MessageHandlingException {
        deliveryStatus.setTo(principal.getName());
        messageService.updateMessageDeliveryStatus(deliveryStatus);

        sendDeliveryStatus(deliveryStatus.getFrom(), deliveryStatus);
    }

    private void sendDeliveryStatus(String to, MessageDeliveryStatus deliveryStatus) {
        toParticipantSender.sendToUserQueue(to, "messages/delivery", deliveryStatus);
    }

//    @MessageMapping("/chat.message")
//    public ChatMessage onUserMessage(Message message, @Payload ChatMessage chatMessage, Principal principal) {
//        messageService.saveMessage(setUpMessage(chatMessage, principal, null, ParticipantType.USER, message));
//        return chatMessage;
//    }

//	@MessageMapping("/chat.private.{login}")
//	public void onUserPrivateMessage(Message message, @Payload ChatMessage chatMessage, @DestinationVariable("login") String login, Principal principal) throws MessageHandlingException {
//        if(!StringUtils.hasText(login)) {
//            throw new InvalidUserLoginException(login);
//        }
//
//        if(!userService.checkUserExists(login)) {
//            throw new UserDoesNotExistException(login);
//        }
//
//        messageService.saveMessage(setUpMessage(chatMessage, principal, login, ParticipantType.USER, message));
//
////		simpMessagingTemplate.convertAndSend("/user/" + login + "/queue/chat.message", chatMessage);
//        toParticipantSender.sendToUserQueue(login, "chat.message", chatMessage);
//	}
	
	@MessageExceptionHandler
	@SendToUser(value = "/queue/errors", broadcast = false)
	public String onMessageHandlingException(MessageHandlingException e) {
		return e.getMessage();
	}

//    @MessageExceptionHandler
//    @SendToUser(value = "/queue/errors", broadcast = false)
//    public String handleEx(InvalidUserLoginException e) {
//        return e.getMessage();
//    }
	
	@RequestMapping("/stats")
	public @ResponseBody WebSocketMessageBrokerStats showStats() {
		return stats;
	}

}