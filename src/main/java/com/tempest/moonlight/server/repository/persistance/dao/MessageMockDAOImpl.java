package com.tempest.moonlight.server.repository.persistance.dao;

import com.tempest.moonlight.server.domain.ChatMessage;
import com.tempest.moonlight.server.domain.MessageKey;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Created by Yurii on 2015-05-08.
 */
@Repository
public class MessageMockDAOImpl extends AbstractMockDAO<ChatMessage, MessageKey> implements MessageDAO {
    @Override
    public Collection<ChatMessage> getMessagesOfUser(String user) {
        return map.entrySet().parallelStream().filter(
                messagesOfUserPredicate(user)
        ).map(Map.Entry::getValue).collect(Collectors.toSet());
    }

    private static Predicate<Map.Entry<MessageKey, ChatMessage>> messagesOfUserPredicate(String user) {
        return entry -> {
            MessageKey key = entry.getKey();
            return user.equals(key.from) || user.equals(key.to) || StringUtils.isEmpty(key.to);
        };
    }
}
