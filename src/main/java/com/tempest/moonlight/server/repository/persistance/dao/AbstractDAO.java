package com.tempest.moonlight.server.repository.persistance.dao;

import com.tempest.moonlight.server.repository.persistance.dao.DAO;
import com.tempest.moonlight.server.repository.persistance.dao.IdentifiedEntity;

import java.io.Serializable;

/**
 * Created by Yurii on 2015-05-07.
 */
public abstract class AbstractDAO<Entity extends IdentifiedEntity<Key>, Key extends Serializable> implements DAO<Entity, Key> {

    @Override
    public Key save(Entity entity) {
        Key key = entity.getKey();
        save(key, entity);
        return key;
    }

    @Override
    public boolean exists(Entity entity) {
        return exists(entity.getKey());
    }

    @Override
    public void delete(Entity entity) {
        delete(entity.getKey());
    }
}
