package com.tempest.moonlight.server.messages.services;

import com.tempest.moonlight.server.domain.contacts.GenericParticipant;
import com.tempest.moonlight.server.domain.messages.ChatMessage;
import com.tempest.moonlight.server.domain.messages.MessageDeliveryStatus;
import com.tempest.moonlight.server.messages.exceptions.MessageHandlingException;

import java.util.Collection;

/**
 * Created by Yurii on 2015-05-08.
 */
public interface MessageService {
    void saveMessage(ChatMessage chatMessage);
    Collection<ChatMessage> getMessagesOfUser(String user);

    Collection<ChatMessage> getMessagesBetween(String user, GenericParticipant companion);
    Collection<ChatMessage> getUndeliveredMessages(String user);

    Collection<ChatMessage> getMessagesOfGroup(String group);
    Collection<ChatMessage> getMessagesOfChannel(String channel);
    void markMessageDelivered(ChatMessage chatMessage, boolean delivered);

    void updateMessageDeliveryStatus(MessageDeliveryStatus deliveryStatus) throws MessageHandlingException;
}
