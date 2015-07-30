package com.tempest.moonlight.server.annotations;

import com.tempest.moonlight.server.common.dto.*;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Yurii on 2015-06-18.
 */
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(DTOs.class)
public @interface DTO {
    EntityDTO.DtoType type();
    Class<? extends EntityDTO> dto();
}
