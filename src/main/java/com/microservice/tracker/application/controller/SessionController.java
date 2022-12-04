package com.microservice.tracker.application.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.tracker.application.request.CreateSessionRequest;
import com.microservice.tracker.application.response.CreateSessionResponse;
import com.microservice.tracker.domain.service.SessionService;

/**
 * Main controller for the tracker application
 */
@RestController
@RequestMapping("/session")
public class SessionController {

    private final SessionService sessionService;

    @Autowired
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    CreateSessionResponse createSession(@RequestBody final CreateSessionRequest createSessionRequest) {
        final UUID sessionId = sessionService.createSession(createSessionRequest.getSession());
        return new CreateSessionResponse(sessionId.toString());
    }
}
