package com.tempest.moonlight.server.common.dto;

import org.springframework.messaging.Message;

/**
 * Created by Yurii on 2015-07-30.
 */
public interface FromMessageConvertedEntity<Entity, Payload> {
    Entity fromMessage(Message<Payload> message);
}
