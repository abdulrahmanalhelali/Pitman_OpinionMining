package com.opinionmining.restservice.controller;

import com.opinionmining.restservice.dto.LoginUserDto;
import com.opinionmining.restservice.dto.SignUpUserDto;
import com.opinionmining.restservice.dto.Token;
import com.opinionmining.restservice.service.CredentialService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(produces = "application/json")
@CrossOrigin("*")
public class BaseController {

    private final CredentialService credentialService;

    public BaseController(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @PostMapping(path = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> signUp(@RequestBody SignUpUserDto user) {
        credentialService.signUp(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path="/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Token> login(@RequestBody LoginUserDto loginUser) {
        Token token = new Token();
        token.setToken(credentialService.login(loginUser));
        return ResponseEntity.ok(token);
    }




}
