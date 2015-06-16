package com.tempest.moonlight.server.services.dto;

import com.tempest.moonlight.server.event.UserSession;

/**
 * Created by Yurii on 2015-06-10.
 */
public class ActiveUserDTO implements ServerToClientDTO<UserSession> {

    private String user;

    @Override
    public void fillWithEntity(UserSession entity) {
        this.user = entity.getLogin();
    }

    @Override
    public String toString() {
        return "ActiveUserDTO{" +
                "user='" + user + '\'' +
                '}';
    }
}
