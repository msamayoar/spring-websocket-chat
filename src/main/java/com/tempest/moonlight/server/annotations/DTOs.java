package com.tempest.moonlight.server.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Yurii on 2015-06-18.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface DTOs {
    DTO[] value();
}
