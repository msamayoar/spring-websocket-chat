package com.tempest.moonlight.server.messages.dao;

import com.tempest.moonlight.server.domain.contacts.GenericParticipant;
import com.tempest.moonlight.server.domain.messages.ChatMessage;
import com.tempest.moonlight.server.domain.MessageKey;
import com.tempest.moonlight.server.common.dao.DAO;

import java.util.Collection;

/**
 * Created by Yurii on 2015-05-08.
 */
public interface MessageDAO extends DAO<ChatMessage, MessageKey> {
    Collection<ChatMessage> getMessagesOfUser(String login);
    Collection<ChatMessage> getMessagesBetween(String user, GenericParticipant companion);
    Collection<ChatMessage> getMessagesTo(GenericParticipant genericParticipant);
}
