package com.tempest.moonlight.server.controllers;

import com.tempest.moonlight.server.domain.ChatMessage;
import com.tempest.moonlight.server.services.MessageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Collection;

@Controller
public class SynchronizationController {

    private static final Logger logger = Logger.getLogger(SynchronizationController.class.getName());

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private MessageService messageService;

    @MessageMapping("/sync/messages")
    @SendToUser
    public void getMessages(Principal principal) {
        logger.error("Sync messages; before sleep");
        try {
            Thread.sleep(5000);
            logger.error("Sync messages; after sleep");

            String name = principal.getName();

            Collection<ChatMessage> messagesOfUser = messageService.getMessagesOfUser(name);
            int x = 10;

            simpMessagingTemplate.convertAndSend("/user/" + name + "/queue/sync.messages", messagesOfUser);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
