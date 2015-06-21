package com.tempest.moonlight.server.websockets;

import com.tempest.moonlight.server.domain.contacts.GenericParticipant;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Created by Yurii on 2015-06-17.
 */
@Component
public class ToParticipantSender {

    private static final Logger logger = Logger.getLogger(ToParticipantSender.class.getName());

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public void sendToUserQueue(String login, String destination, Object message) {
        logger.error("sendToUserQueue(" + "login = [" + login + "], destination = [" + destination + "], message = [" + message + "]" + ")");
        simpMessagingTemplate.convertAndSend("/user/" + login + "/queue/" + destination, message);
    }

    public void sendToUserQueue(GenericParticipant participant, String destination, Object message) {
        sendToUserQueue(participant.getSignature(), destination, message);
    }

    public void sendToUsersQueue(Collection<GenericParticipant> participants, String destination, Object message) {
        participants.forEach(participant -> sendToUserQueue(participant, destination, message));
    }

    public void sendToUsersQueue(Map<GenericParticipant, ?> participantMessageMap, String destination) {
        participantMessageMap.forEach((participant, message) -> sendToUserQueue(participant, destination, message));
    }
}
