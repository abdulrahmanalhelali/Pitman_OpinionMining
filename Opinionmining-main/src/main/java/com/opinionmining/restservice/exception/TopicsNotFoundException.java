package com.opinionmining.restservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Topics with given user id are not found.")
public class TopicsNotFoundException extends RuntimeException{

}
