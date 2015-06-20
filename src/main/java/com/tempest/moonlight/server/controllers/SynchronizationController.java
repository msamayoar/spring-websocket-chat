package com.tempest.moonlight.server.controllers;

import com.tempest.moonlight.server.domain.messages.ChatMessage;
import com.tempest.moonlight.server.services.messages.MessageService;
import com.tempest.moonlight.server.websockets.ToUserSender;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Collection;

@Controller
public class SynchronizationController {

    private static final Logger logger = Logger.getLogger(SynchronizationController.class.getName());

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private ToUserSender toUserSender;

    @Autowired
    private MessageService messageService;

    @MessageMapping("/sync/messages")
    public void getMessages(Principal principal) {
        String name = principal.getName();
        Collection<ChatMessage> messagesOfUser = messageService.getMessagesOfUser(name);
//        simpMessagingTemplate.convertAndSend("/user/" + name + "/queue/sync.messages", messagesOfUser);
        toUserSender.sendToUserQueue(name, "sync/messages", messagesOfUser);
    }
}
