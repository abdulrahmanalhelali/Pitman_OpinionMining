package com.opinionmining.restservice.controller;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.opinionmining.restservice.entity.Topics;
import com.opinionmining.restservice.security.JwtValidator;
import com.opinionmining.restservice.service.TopicsService;


@RestController
@RequestMapping(path = "/topics",produces = "application/json")
@CrossOrigin("*")
public class TopicsController {
	
	private final TopicsService topicsService;

    public TopicsController(TopicsService topicsService){
        this.topicsService = topicsService;
    }
    
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Topics>> getTopicsByUser(@RequestHeader("Authorization") String token) {
		JwtValidator validator = new JwtValidator();
		this.topicsService.findByUserId(validator.validate(token).getId());
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
