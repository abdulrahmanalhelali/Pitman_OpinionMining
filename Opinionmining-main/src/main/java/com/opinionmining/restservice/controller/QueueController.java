package com.opinionmining.restservice.controller;

import com.opinionmining.restservice.security.JwtValidator;
import com.opinionmining.restservice.service.QueueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/queue",produces = "application/json")
@CrossOrigin("*")
public class QueueController {

    private final QueueService queueService;

    public QueueController(QueueService queueService) {
        this.queueService = queueService;
    }

    @PutMapping("/{platform}")
    public ResponseEntity<Void> addToQueue(@RequestHeader("Authorization") String token,
                                           @PathVariable String platform) {
        JwtValidator validator = new JwtValidator();
        queueService.addToQueue(validator.validate(token).getUsername(), platform);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
