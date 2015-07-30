package com.tempest.moonlight.server.exceptions.local.dto;

import com.tempest.moonlight.server.common.dto.EntityDTO;

/**
 * Created by Yurii on 2015-06-19.
 */
public class NoEntityClassFoundException extends DtoException {
    public NoEntityClassFoundException(Class<? extends EntityDTO> dtoClass) {
        super("No entity found with DTO class = " + dtoClass.getName());
    }
}
