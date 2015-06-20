package com.tempest.moonlight.server.controllers;

import com.tempest.moonlight.server.exceptions.UserAlreadyExistsException;
import com.tempest.moonlight.server.services.users.UserService;
import com.tempest.moonlight.server.services.dto.RegistrationDTO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistrationController {

    private static final Logger logger = Logger.getLogger(RegistrationController.class.getName());

    @Autowired
    private UserService userService;

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<String> registerNewUser(@RequestBody RegistrationDTO registrationDTO) {
        String login = registrationDTO.getLogin();
        if(StringUtils.isEmpty(login)) {
            logger.error("Registration login is empty");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        String password = registrationDTO.getPassword();
        if(StringUtils.isEmpty(password)) {
            logger.error("Registration password is empty");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if(!password.equals(registrationDTO.getPasswordConfirmation())) {
            logger.error("Password and its confirmation do not match");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        try {
            return new ResponseEntity<>(userService.registerUser(login, registrationDTO.getPassword()).getLogin(), HttpStatus.OK);
        } catch (UserAlreadyExistsException e) {
            logger.error("User with login=" + login + " already exists", e);
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

}
