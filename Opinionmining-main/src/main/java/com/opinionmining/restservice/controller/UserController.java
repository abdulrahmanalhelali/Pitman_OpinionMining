package com.opinionmining.restservice.controller;

import com.opinionmining.restservice.dto.Token;
import com.opinionmining.restservice.security.JwtValidator;
import com.opinionmining.restservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user", produces = "application/json")
@CrossOrigin("*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PutMapping("/fb")
    public ResponseEntity<Void> setFacebookToken(@RequestHeader("Authorization") String token,
                                                 @RequestBody Token fbToken){
        JwtValidator validator = new JwtValidator();
        userService.saveFbToken(validator.validate(token), fbToken.getToken());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/tw")
    public ResponseEntity<Void> setTwitterToken(@RequestHeader("Authorization") String token,
                                                 @RequestBody Token twToken){
        JwtValidator validator = new JwtValidator();
        userService.saveTwToken(validator.validate(token), twToken.getToken());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
