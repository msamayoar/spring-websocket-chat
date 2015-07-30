package com.tempest.moonlight.server.common.dto;

/**
 * Created by Yurii on 2015-06-10.
 */
public interface BiDirectionalDTO<Entity> extends ClientToServerDTO<Entity>, ServerToClientDTO<Entity> {
}
