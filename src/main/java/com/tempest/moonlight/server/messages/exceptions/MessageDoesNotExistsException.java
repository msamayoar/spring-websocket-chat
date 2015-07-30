package com.tempest.moonlight.server.messages.exceptions;

import com.tempest.moonlight.server.domain.MessageKey;

/**
 * Created by Yurii on 2015-06-20.
 */
public class MessageDoesNotExistsException extends MessageHandlingException {

    public MessageDoesNotExistsException(MessageKey messageKey) {
        super("Message with specified key does not exists: " + messageKey);
    }
}
