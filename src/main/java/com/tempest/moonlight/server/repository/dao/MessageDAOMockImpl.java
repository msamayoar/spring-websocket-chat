package com.tempest.moonlight.server.repository.dao;

import com.tempest.moonlight.server.domain.messages.ChatMessage;
import com.tempest.moonlight.server.domain.MessageKey;
import com.tempest.moonlight.server.util.CollectionsUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Created by Yurii on 2015-05-08.
 */
@Repository
public class MessageDAOMockImpl extends AbstractMockDAO<ChatMessage, MessageKey> implements MessageDAO {
    @Override
    public Collection<ChatMessage> getMessagesOfUser(String login) {
        return CollectionsUtils.filterMapValues(getMap(), messagesOfUserPredicate(login));
    }

    private static Predicate<Map.Entry<MessageKey, ChatMessage>> messagesOfUserPredicate(String user) {
        return entry -> {
            MessageKey key = entry.getKey();
            return user.equals(key.from) || user.equals(key.to) || StringUtils.isEmpty(key.to);
        };
    }
}
