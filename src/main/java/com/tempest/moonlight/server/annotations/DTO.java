package com.tempest.moonlight.server.annotations;

import com.tempest.moonlight.server.services.dto.DTOType;
import com.tempest.moonlight.server.services.dto.EntityDTO;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Yurii on 2015-06-18.
 */
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(DTOs.class)
public @interface DTO {
    DTOType type = DTOType.BiDir;
    Class<? extends EntityDTO> dto = null;
}
