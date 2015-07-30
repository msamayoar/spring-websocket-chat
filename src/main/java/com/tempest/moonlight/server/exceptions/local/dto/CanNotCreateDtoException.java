package com.tempest.moonlight.server.exceptions.local.dto;

import com.tempest.moonlight.server.common.dto.EntityDTO;

/**
 * Created by Yurii on 2015-06-19.
 */
public class CanNotCreateDtoException extends DtoException {

    public CanNotCreateDtoException(Class entityClass, Class<? extends EntityDTO> dtoClass, Exception cause) {
        super("Can not instantiate DTO instance of class = " + dtoClass + " for entity class = " + entityClass.getName(), cause);
    }
}
