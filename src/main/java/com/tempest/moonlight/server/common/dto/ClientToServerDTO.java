package com.tempest.moonlight.server.common.dto;

/**
 * Created by Yurii on 2015-06-10.
 */
public interface ClientToServerDTO<Entity> extends EntityDTO<Entity> {
    void fillEntity(Entity entity);
}
