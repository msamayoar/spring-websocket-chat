package com.tempest.moonlight.server.services.dto;

/**
 * Created by Yurii on 2015-06-10.
 */
public interface BiDirectionalDTO<Entity> extends ClientToServerDTO<Entity>, ServerToClientDTO<Entity> {
}
