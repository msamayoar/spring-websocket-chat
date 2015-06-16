package com.tempest.moonlight.server.repository.persistence.dao;

/**
 * Created by Yurii on 2015-05-07.
 */
public interface IdentifiedEntity<Key> {
    Key getKey();
}
