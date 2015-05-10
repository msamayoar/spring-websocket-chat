package com.tempest.moonlight.server.repository.persistance.dao;

import java.io.Serializable;

/**
 * Created by Yurii on 4/21/2015.
 */
public interface DAO<Entity, Key extends Serializable> {

    void save(Key key, Entity entity);

    Key save(Entity entity);

    Entity get(Key key);

    boolean exists(Key key);

    boolean exists(Entity entity);

    void delete(Key key);

    void delete(Entity entity);
}
