package com.tempest.moonlight.server.util;

import com.tempest.moonlight.server.domain.ChatMessage;
import com.tempest.moonlight.server.domain.ParticipantType;
import com.tempest.moonlight.server.websockets.CustomMessageHeadersAccessor;
import org.springframework.messaging.Message;

import java.security.Principal;

/**
 * Created by Yurii on 2015-05-08.
 */
public class ChatMessageUtil {
    public static ChatMessage setUpMessage(ChatMessage chatMessage, Principal sender, String to, ParticipantType type, Message message) {
        CustomMessageHeadersAccessor headerAccessor = CustomMessageHeadersAccessor.wrap(message);
        headerAccessor.setImmutable();
        return chatMessage.setUp(sender.getName(), to, type, headerAccessor.getTimestamp(), headerAccessor.getId().toString());
    }
}
