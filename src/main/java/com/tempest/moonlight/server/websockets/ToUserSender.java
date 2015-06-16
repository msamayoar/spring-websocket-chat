package com.tempest.moonlight.server.websockets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by Yurii on 2015-06-17.
 */
@Component
public class ToUserSender {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public void sendToUserQueue(String login, String destination, Object message) {
        simpMessagingTemplate.convertAndSend("/user/" + login + "/queue/" + destination, message);
    }
}
