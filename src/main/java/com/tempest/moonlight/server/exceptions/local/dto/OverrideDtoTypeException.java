package com.tempest.moonlight.server.exceptions.local.dto;

import com.tempest.moonlight.server.common.dto.EntityDTO;

/**
 * Created by Yurii on 2015-06-18.
 */
public class OverrideDtoTypeException extends DtoException {

    public OverrideDtoTypeException(Class entityClass, EntityDTO.DtoType dtoType, Class<? extends EntityDTO> dtoClass, Class<? extends EntityDTO> overridenDtoClass) {
        super("Dto of type = " + dtoType + " and class = " + dtoClass.getName() + " is overriden in class = " + entityClass.getName() + " by another dto class = " + overridenDtoClass.getName());
    }
}
