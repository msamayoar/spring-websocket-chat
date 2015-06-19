package com.tempest.moonlight.server.exceptions.local.dto;

import com.tempest.moonlight.server.services.dto.EntityDTO;

/**
 * Created by Yurii on 2015-06-18.
 */
public class IncorrectDtoTypeException extends DtoException {

    public IncorrectDtoTypeException(Class entityClass, EntityDTO.DtoType dtoType, Class<? extends EntityDTO> dtoClass) {
        super("Incorrect DTO type for class " + entityClass.getName() + ". Declared type = " + dtoType + ", found class = " + dtoClass + " which does not implements interface " + dtoType.dtoClass);
    }
}
