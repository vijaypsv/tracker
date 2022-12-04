package com.microservice.tracker.application.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.tracker.application.request.AddEventBatchRequest;
import com.microservice.tracker.application.request.AddEventRequest;
import com.microservice.tracker.application.request.CreateSessionRequest;
import com.microservice.tracker.application.request.EndSessionRequest;
import com.microservice.tracker.application.response.CreateSessionResponse;
import com.microservice.tracker.application.validator.Uuid;
import com.microservice.tracker.domain.model.Session;
import com.microservice.tracker.domain.service.SessionService;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Main controller for the tracker application
 */
@RestController
@RequestMapping("/session")
@Tag(name = "Tracking Microservice", description = "Tracks microservice activity")
public class SessionController {
    private final SessionService sessionService;

    @Autowired
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Tracking Microservice", value = "Creates a new user session in a specific machine")
    CreateSessionResponse createSession(@RequestBody @Valid final CreateSessionRequest createSessionRequest) {
        final UUID sessionId = sessionService.createSession(createSessionRequest.getSession());
        return new CreateSessionResponse(sessionId.toString());
    }

    @GetMapping(value = "/")
    @ApiOperation(tags = "Tracking Microservice", value = "Gests a list of all the sesions")
    List<Session> getSessions() {
        return sessionService.getSessions();
    }

    @GetMapping(value = "/{sessionId}")
    @ApiOperation(tags = "Tracking Microservice", value = "Gets a session by its id")
    Session getSession(@PathVariable @Uuid @Valid final String sessionId) {
        return sessionService.getSession(UUID.fromString(sessionId));
    }

    @PutMapping(value = "/{sessionId}/end")
    @ApiOperation(tags = "Tracking Microservice", value = "Ends a session")
    void endSession(@PathVariable final UUID sessionId, @RequestBody final EndSessionRequest endSessionRequest) {
        sessionService.endSession(sessionId, endSessionRequest.getEndtAt());
    }

    @PutMapping(value = "/{sessionId}/events")
    @ApiOperation(tags = "Tracking Microservice", value = "Adds an event to a session")
    void addEvent(@PathVariable final UUID sessionId, @RequestBody @Valid final AddEventRequest addEventRequest) {
        sessionService.addEvent(sessionId, addEventRequest.getEvent());
    }

    @PutMapping(value = "/{sessionId}/events/batch")
    @ApiOperation(tags = "Tracking Microservice", value = "Adds multiple events to a session")
    void addEventBatch(@PathVariable final UUID sessionId, @RequestBody @Valid final AddEventBatchRequest addEventBatchRequest) {
        sessionService.addEvents(sessionId, addEventBatchRequest.getEvents());
    }
}
