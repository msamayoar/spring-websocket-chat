package com.tempest.moonlight.server.repository.persistence.dao;

import java.io.Serializable;

/**
 * Created by Yurii on 2015-06-10.
 */
public interface IdentifiedEntityDAO<Entity extends IdentifiedEntity<Key>, Key extends Serializable> extends DAO<Entity, Key> {
}
