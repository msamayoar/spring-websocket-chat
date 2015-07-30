package com.tempest.moonlight.server.messages.exceptions;

/**
 * Created by Yurii on 2015-06-22.
 */
public class IllegalGroupRecipientException extends MessageHandlingException {
    public IllegalGroupRecipientException(String groupSignature) {
        super("Illegal message to group with signature = " + groupSignature);
    }
}
