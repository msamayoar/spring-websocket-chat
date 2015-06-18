package com.tempest.moonlight.server.controllers;

import com.tempest.moonlight.server.services.UserService;
import com.tempest.moonlight.server.services.dto.RegistrationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;

/**
 * Created by Yurii on 2015-05-09.
 */
@Controller
public class TestDataController {

    @Autowired
    private UserService userService;

    @PostConstruct
    public void addTestData() {
        userService.registerUser("azaza", "azazap");
        userService.registerUser("a", "1");
    }

}
