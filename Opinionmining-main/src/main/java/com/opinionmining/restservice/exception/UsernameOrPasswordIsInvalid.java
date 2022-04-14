package com.opinionmining.restservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Username or password is not correct.")
public class UsernameOrPasswordIsInvalid extends RuntimeException {
}
