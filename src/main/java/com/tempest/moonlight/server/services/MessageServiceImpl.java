package com.tempest.moonlight.server.services;

import com.tempest.moonlight.server.domain.ChatMessage;
import com.tempest.moonlight.server.repository.persistance.dao.MessageDAO;
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
}
