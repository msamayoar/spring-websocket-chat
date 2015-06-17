package com.tempest.moonlight.server.repository.dao;

import com.tempest.moonlight.server.domain.ChatMessage;
import com.tempest.moonlight.server.domain.MessageKey;

import java.util.Collection;

/**
 * Created by Yurii on 2015-05-08.
 */
public interface MessageDAO extends DAO<ChatMessage, MessageKey> {
    Collection<ChatMessage> getMessagesOfUser(String user);
}
