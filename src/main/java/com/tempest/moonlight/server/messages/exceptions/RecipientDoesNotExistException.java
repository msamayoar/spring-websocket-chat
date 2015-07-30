package com.tempest.moonlight.server.messages.exceptions;

import com.tempest.moonlight.server.domain.ParticipantType;

/**
 * Created by Yurii on 2015-05-08.
 */
public class RecipientDoesNotExistException extends MessageHandlingException {
    public RecipientDoesNotExistException(ParticipantType participantType, String username) {
        super("Recipient of type = " + participantType + " with login '" + username + "' does not exist.");
    }
}
