package com.tempest.moonlight.server.repository.persistance.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Yurii on 2015-05-07.
 */
public abstract class AbstractMockDAO<Entity extends IdentifiedEntity<Key>, Key extends Serializable> extends AbstractDAO<Entity, Key> {

    protected Map<Key, Entity> map;

    public AbstractMockDAO() {
        map = new ConcurrentHashMap<>();
    }

    @Override
    public void save(Key key, Entity entity) {
        map.put(key, entity);
    }

    @Override
    public Entity get(Key key) {
        return map.get(key);
    }

    @Override
    public boolean exists(Key key) {
        return map.containsKey(key);
    }

    @Override
    public void delete(Key key) {
        map.remove(key);
    }
}
