package com.tempest.moonlight.server.users.dto;

/**
 * Created by Yurii on 2015-05-08.
 */
public class RegistrationDTO {
    private String login;
    private String password;
    private String passwordConfirmation;

    public RegistrationDTO() {
    }

    public RegistrationDTO(String login, String password, String passwordConfirmation) {
        this.login = login;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
    }

    public String getLogin() {
        return login;
    }

    public RegistrationDTO setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RegistrationDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public RegistrationDTO setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
        return this;
    }
}
