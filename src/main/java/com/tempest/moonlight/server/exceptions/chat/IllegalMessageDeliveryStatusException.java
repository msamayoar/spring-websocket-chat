package com.tempest.moonlight.server.exceptions.chat;

import com.tempest.moonlight.server.domain.messages.MessageStatus;

/**
 * Created by Yurii on 2015-06-20.
 */
public class IllegalMessageDeliveryStatusException extends MessageHandlingException {
    public IllegalMessageDeliveryStatusException(MessageStatus messageStatus) {
        super("Illegal message delivery status = " + messageStatus + ". Only [" + MessageStatus.DELIVERED + ", " + MessageStatus.READ + "] are allowed");
    }
}
