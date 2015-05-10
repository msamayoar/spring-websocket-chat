package com.tempest.moonlight.server.repository.persistance.dao;

/**
 * Created by Yurii on 2015-05-07.
 */
public interface IdentifiedEntity<Key> {
    Key getKey();
}
