package com.tempest.moonlight.server.exceptions.local.dto;

import com.tempest.moonlight.server.common.dto.EntityDTO;

/**
 * Created by Yurii on 2015-06-19.
 */
public class NoDtoClassFoundException extends DtoException {

    public NoDtoClassFoundException(Class entityClass) {
        super("No DTOs found for class = " + entityClass.getName());
    }

    public NoDtoClassFoundException(Class entityClass, EntityDTO.DtoType dtoType) {
        super("No DTO found for class = " + entityClass.getName() + " with type = " + dtoType);
    }
}
