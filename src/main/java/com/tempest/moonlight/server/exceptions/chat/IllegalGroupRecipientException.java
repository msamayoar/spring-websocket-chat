package com.tempest.moonlight.server.exceptions.chat;

/**
 * Created by Yurii on 2015-06-22.
 */
public class IllegalGroupRecipientException extends MessageHandlingException {
    public IllegalGroupRecipientException(String groupSignature) {
        super("Illegal message to group with signature = " + groupSignature);
    }
}
