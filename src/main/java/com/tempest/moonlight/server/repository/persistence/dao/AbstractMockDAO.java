package com.tempest.moonlight.server.repository.persistence.dao;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Yurii on 2015-05-07.
 */
public abstract class AbstractMockDAO<Entity extends IdentifiedEntity<Key>, Key extends Serializable> extends AbstractIdentifiedEntityDAO<Entity, Key> {

    private Map<Key, Entity> map;

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
    public boolean existsWithKey(Key key) {
        return map.containsKey(key);
    }

    @Override
    public boolean deleteWithKey(Key key) {
        return map.remove(key) != null;
    }

    protected Map<Key, Entity> getMap() {
        return map;
    }
}
