package com.tempest.moonlight.server.security;

import com.tempest.moonlight.server.users.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Arrays;

/**
 * Created by Yurii on 2015-05-08.
 */
@Component
public class CustomAuthenticationManager implements AuthenticationManager {

    private static final Logger logger = Logger.getLogger(CustomAuthenticationManager.class.getName());

    @Autowired
    private UserService userService;


    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(UsernamePasswordAuthenticationToken.class, authentication, "Unsupported authentication type");
        Assert.isTrue(!authentication.isAuthenticated(), "Already authenticated");
        String key = authentication.getPrincipal().toString();
        if (!StringUtils.hasText(key)) {
            throw new BadCredentialsException("User key must not be empty.");
        }
//        if (!Optional.ofNullable(userDAO.existsWithKey(key)).isPresent()) {
        if (!userService.checkUserExists(key)) {
            throw new UsernameNotFoundException("User does not exist in database.");
        }

        if(!userService.checkUserPassword(key, (String) authentication.getCredentials())) {
            throw new BadCredentialsException("User's login or password not correct");
        }

        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                token.getPrincipal(),
                token.getPrincipal(),
                Arrays.asList(new SimpleGrantedAuthority("USER"))
        );
        return auth;
    }
}
