package com.tempest.moonlight.server.websockets;

import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.support.IdTimestampMessageHeaderInitializer;
import org.springframework.util.AlternativeJdkIdGenerator;

import java.util.List;
import java.util.Map;

/**
 * Created by Yurii on 2015-05-07.
 */
public class CustomMessageHeadersAccessor extends SimpMessageHeaderAccessor {

    public static final IdTimestampMessageHeaderInitializer HEADER_INITIALIZER;

    static {
        HEADER_INITIALIZER = new IdTimestampMessageHeaderInitializer();
        HEADER_INITIALIZER.setIdGenerator(new AlternativeJdkIdGenerator());
        HEADER_INITIALIZER.setEnableTimestamp(true);
    }

    protected CustomMessageHeadersAccessor(SimpMessageType messageType, Map<String, List<String>> externalSourceHeaders) {
        super(messageType, externalSourceHeaders);
        HEADER_INITIALIZER.initHeaders(this);
    }

    protected CustomMessageHeadersAccessor(Message<?> message) {
        super(message);
        HEADER_INITIALIZER.initHeaders(this);
    }

    public static CustomMessageHeadersAccessor wrap(Message<?> message) {
        return new CustomMessageHeadersAccessor(message);
    }
}
