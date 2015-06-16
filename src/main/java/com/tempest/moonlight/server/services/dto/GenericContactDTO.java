package com.tempest.moonlight.server.services.dto;

import com.tempest.moonlight.server.domain.GenericContact;

/**
 * Created by Yurii on 2015-06-16.
 */
public class GenericContactDTO implements ServerToClientDTO<GenericContact> {
    private int type;
    private String id;

    public GenericContactDTO() {
    }

    public GenericContactDTO(int type, String id) {
        this.type = type;
        this.id = id;
    }

    @Override
    public void fillWithEntity(GenericContact entity) {
        type =  entity.getType().getValue();
        id = entity.getId();
    }

    @Override
    public String toString() {
        return "GenericContactDTO{" +
                "type=" + type +
                ", id='" + id + '\'' +
                '}';
    }

    public int getType() {
        return type;
    }

    public GenericContactDTO setType(int type) {
        this.type = type;
        return this;
    }

    public String getId() {
        return id;
    }

    public GenericContactDTO setId(String id) {
        this.id = id;
        return this;
    }
}
