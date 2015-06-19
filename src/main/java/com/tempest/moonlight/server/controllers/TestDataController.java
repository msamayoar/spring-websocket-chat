package com.tempest.moonlight.server.controllers;

import com.tempest.moonlight.server.services.UserService;
import com.tempest.moonlight.server.services.dto.RegistrationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;

@Controller
public class TestDataController {

    @Autowired
    private UserService userService;

    @PostConstruct
    public void addTestData() {
        userService.registerUser("user1", "user1p");
        userService.registerUser("a", "1");
    }

}
