package com.tempest.moonlight.server.messages.services;

import com.tempest.moonlight.server.domain.MessageKey;
import com.tempest.moonlight.server.domain.ParticipantType;
import com.tempest.moonlight.server.domain.contacts.GenericParticipant;
import com.tempest.moonlight.server.domain.messages.ChatMessage;
import com.tempest.moonlight.server.domain.messages.MessageDeliveryStatus;
import com.tempest.moonlight.server.domain.messages.MessageStatus;
import com.tempest.moonlight.server.messages.exceptions.IllegalMessageDeliveryStatusException;
import com.tempest.moonlight.server.messages.exceptions.MessageDoesNotExistsException;
import com.tempest.moonlight.server.messages.exceptions.MessageHandlingException;
import com.tempest.moonlight.server.messages.dao.MessageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collection;

/**
 * Created by Yurii on 2015-05-08.
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageDAO messageDAO;

    @Override
    public void saveMessage(ChatMessage chatMessage) {
        if(chatMessage == null) {
            throw new NullPointerException();
        }
        messageDAO.save(chatMessage);
    }

    @Override
    public Collection<ChatMessage> getMessagesOfUser(String user) {
        return StringUtils.isEmpty(user) ? null : messageDAO.getMessagesOfUser(user);
    }

    @Override
    public Collection<ChatMessage> getMessagesBetween(String user, GenericParticipant companion) {
        if(companion.getType() == ParticipantType.GROUP) {
            return getMessagesOfGroup(companion.getSignature());
        } else {
            return messageDAO.getMessagesBetween(user, companion);
        }
    }

    @Override
    public Collection<ChatMessage> getUndeliveredMessages(String user) {
        return null;
    }

    @Override
    public Collection<ChatMessage> getMessagesOfGroup(String group) {
        return messageDAO.getMessagesTo(new GenericParticipant(ParticipantType.GROUP, group));
    }

    @Override
    public Collection<ChatMessage> getMessagesOfChannel(String channel) {
        return null;
    }

    @Override
    public void markMessageDelivered(ChatMessage chatMessage, boolean delivered) {

    }

    @Override
    public void updateMessageDeliveryStatus(MessageDeliveryStatus deliveryStatus) throws MessageHandlingException {
        MessageKey messageKey = MessageKey.fromDeliveryStatus(deliveryStatus);
        if(!messageDAO.existsWithKey(messageKey)) {
            throw new MessageDoesNotExistsException(messageKey);
        }

        ChatMessage chatMessage = messageDAO.get(messageKey);
        MessageStatus status = MessageStatus.getByValue(deliveryStatus.getStatus());
        if(!(status == MessageStatus.DELIVERED || status == MessageStatus.READ)) {
            throw new IllegalMessageDeliveryStatusException(status);
        }
        //TODO check if user downgrades status!
        chatMessage.setStatus(status);
    }
}
