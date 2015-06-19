package com.tempest.moonlight.server.exceptions.local.dto;

import com.tempest.moonlight.server.services.dto.EntityDTO;

/**
 * Created by Yurii on 2015-06-19.
 */
public class UnsupportedDtoTypeException extends DtoException {
    public UnsupportedDtoTypeException(EntityDTO.DtoType dtoType) {
        super("Unsupported DTO type = " + dtoType);
    }
}
