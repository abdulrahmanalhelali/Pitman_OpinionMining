package com.opinionmining.restservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "This username is already exist.")
public class UsernameIsTakenException extends RuntimeException {

}
