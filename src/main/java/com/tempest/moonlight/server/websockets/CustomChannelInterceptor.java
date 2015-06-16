package com.tempest.moonlight.server.websockets;

import org.apache.log4j.Logger;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageBuilder;

import java.util.UUID;

/**
 * Created by Yurii on 2015-05-10.
 */
public class CustomChannelInterceptor extends ChannelInterceptorAdapter {

    private static final Logger logger = Logger.getLogger(CustomChannelInterceptor.class.getName());

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        super.preSend(message, channel);

//        logger.error("\n");

//        CustomMessageHeadersAccessor accessor = CustomMessageHeadersAccessor.wrap(message);
        MessageHeaders headers = message.getHeaders();
        Long timestamp = headers.getTimestamp();
//        logger.error("preSend! => id = " + headers.getId() + ", time = " + timestamp);

//        if(timestamp == null || timestamp == -1 || timestamp == 0 || headers.getId() == null) {
//            return CustomMessageHeadersAccessor.makeWithIdAndTimestamp(message);
//        } else {
//            return message;
//        }
        return message;
    }

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        super.postSend(message, channel, sent);

        CustomMessageHeadersAccessor accessor = CustomMessageHeadersAccessor.wrap(message);
//        logger.error("\n");
//        logger.error("channel = " + channel);
//        logger.error("payload = " + message.getPayload());
        Long timestamp = accessor.getTimestamp();
//        logger.error("postSend! => id = " + accessor.getId() + ", time = " + timestamp);
        if(timestamp == null || timestamp == -1 || timestamp == 0) {
            accessor.setImmutable();
//            logger.error("postSend! After setImmutable() => id = " + accessor.getId() + ", time = " + accessor.getTimestamp());
        }
    }

    @Override
    public Message<?> postReceive(Message<?> message, MessageChannel channel) {
        super.postReceive(message, channel);

        CustomMessageHeadersAccessor accessor = CustomMessageHeadersAccessor.wrap(message);
//        logger.error("\n");
//        logger.error("channel = " + channel);
//        logger.error("payload = " + message.getPayload());

        Long timestamp = accessor.getTimestamp();
//        logger.error("postReceive! => id = " + accessor.getId() + ", time = " + timestamp);
        if(timestamp == null || timestamp == -1 || timestamp == 0) {
            accessor.setImmutable();
//            logger.error("postReceive! After setImmutable() => id = " + accessor.getId() + ", time = " + accessor.getTimestamp());
        }
        return message;
    }
}
