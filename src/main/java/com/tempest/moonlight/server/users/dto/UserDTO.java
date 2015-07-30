package com.tempest.moonlight.server.users.dto;

import com.tempest.moonlight.server.domain.User;
import com.tempest.moonlight.server.common.dto.ServerToClientDTO;

/**
 * Created by Yurii on 2015-06-21.
 */
public class UserDTO implements ServerToClientDTO<User> {

    private String login;

    public UserDTO() {
    }

    public UserDTO(String login) {
        this.login = login;
    }

    @Override
    public void fillWithEntity(User user) {
        login = user.getLogin();
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "login='" + login + '\'' +
                '}';
    }

    public String getLogin() {
        return login;
    }

    public UserDTO setLogin(String login) {
        this.login = login;
        return this;
    }
}
