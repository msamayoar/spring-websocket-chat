package com.tempest.moonlight.server.exceptions.local.dto;

import com.tempest.moonlight.server.services.dto.EntityDTO;

/**
 * Created by Yurii on 2015-06-19.
 */
public class CanNotCreateEntityException extends DtoException {
    public CanNotCreateEntityException(Class entityClass, Class<? extends EntityDTO> dtoClass, Exception cause) {
        super("Can not instantiate entity instance of class = " + entityClass.getName() + " for DTO class = " + dtoClass, cause);
    }
}
