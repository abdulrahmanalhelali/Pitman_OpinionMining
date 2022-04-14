package com.opinionmining.restservice.service;

import com.opinionmining.restservice.dto.LoginUserDto;
import com.opinionmining.restservice.dto.SignUpUserDto;
import com.opinionmining.restservice.entity.User;
import com.opinionmining.restservice.model.JwtUser;
import com.opinionmining.restservice.security.JwtGenerator;
import com.opinionmining.restservice.security.JwtValidator;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class CredentialService {

    private final UserService userService;


    public CredentialService(UserService userService) {
        this.userService = userService;
    }

    public void signUp(SignUpUserDto user) {
        userService.signup(user);
    }

    public String login(LoginUserDto user) {
        return userService.login(user);
    }

}