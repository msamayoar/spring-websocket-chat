package com.tempest.moonlight.server.controllers;

import java.security.Principal;
import java.util.Collection;

import com.tempest.moonlight.server.exceptions.InvalidUserLoginException;
import com.tempest.moonlight.server.exceptions.MessageHandlingException;
import com.tempest.moonlight.server.exceptions.UserDoesNotExistException;
import com.tempest.moonlight.server.repository.persistence.dao.ActiveUsersDAO;
import com.tempest.moonlight.server.services.MessageService;
import com.tempest.moonlight.server.services.UserService;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.config.WebSocketMessageBrokerStats;

import com.tempest.moonlight.server.domain.ChatMessage;
//import com.tempest.moonlight.server.domain.SessionProfanity;
import com.tempest.moonlight.server.event.UserSession;
//import com.tempest.moonlight.server.util.ProfanityChecker;

import static com.tempest.moonlight.server.util.ChatMessageUtil.setUpMessage;

@Controller
public class ChatController {
	private static final Logger logger = Logger.getLogger(ChatController.class.getName());

//	@Autowired
//    private ProfanityChecker profanityFilter;

//	@Autowired
//    private SessionProfanity profanity;

	@Autowired
    private WebSocketMessageBrokerStats stats;

	@Autowired
    private ActiveUsersDAO activeUsersDAO;
//    private ParticipantRepository participantRepository;


	@Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;
	
	@SubscribeMapping("/chat.participants")
	public Collection<String> retrieveParticipants(Principal principal) {
//		return activeUsersDAO.getActiveSessions();
        Collection<String> allActiveUsers = activeUsersDAO.getActiveUsers();
        allActiveUsers.remove(principal.getName());
        return allActiveUsers;
    }
	
	@MessageMapping("/chat.message")
	public ChatMessage filterMessage(Message message, @Payload ChatMessage chatMessage, Principal principal) {
//		checkProfanityAndSanitize(chatMessage);

        messageService.saveMessage(setUpMessage(chatMessage, principal, null, message));
		
		return chatMessage;
	}


	
	@MessageMapping("/chat.private.{login}")
	public void filterPrivateMessage(Message message, @Payload ChatMessage chatMessage, @DestinationVariable("login") String login, Principal principal) {
        if(!StringUtils.hasText(login)) {
            throw new InvalidUserLoginException(login);
        }

        if(!userService.checkUserExists(login)) {
            throw new UserDoesNotExistException(login);
        }

//        checkProfanityAndSanitize(chatMessage);

        messageService.saveMessage(setUpMessage(chatMessage, principal, login, message));

		simpMessagingTemplate.convertAndSend("/user/" + login + "/queue/chat.message", chatMessage);
	}
	
//	private void checkProfanityAndSanitize(ChatMessage message) {
//		long profanityLevel = profanityFilter.getMessageProfanity(message.getText());
//		profanity.increment(profanityLevel);
//		message.setText(profanityFilter.filter(message.getText()));
//	}
	
	@MessageExceptionHandler
	@SendToUser(value = "/queue/errors", broadcast = false)
	public String handleProfanity(MessageHandlingException e) {
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