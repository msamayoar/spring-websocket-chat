package com.tempest.moonlight.server.common.dao;

import java.io.Serializable;

/**
 * Created by Yurii on 2015-05-07.
 */
public abstract class AbstractIdentifiedEntityDAO<Entity extends IdentifiedEntity<Key>, Key extends Serializable> implements DAO<Entity, Key> {

    @Override
    public Key save(Entity entity) {
        Key key = entity.getKey();
        save(key, entity);
        return key;
    }

    @Override
    public boolean exists(Entity entity) {
        return existsWithKey(entity.getKey());
    }

    @Override
    public boolean delete(Entity entity) {
        return deleteWithKey(entity.getKey());
    }
}
