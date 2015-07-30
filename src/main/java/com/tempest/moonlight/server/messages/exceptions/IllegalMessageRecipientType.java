package com.tempest.moonlight.server.messages.exceptions;

import com.tempest.moonlight.server.domain.ParticipantType;

/**
 * Created by Yurii on 2015-06-20.
 */
public class IllegalMessageRecipientType extends MessageHandlingException {
    public IllegalMessageRecipientType(ParticipantType participantType) {
        super("Illegal participant type = " + participantType);
    }
}
