package com.microservice.tracker.application.response;

import java.sql.Timestamp;

import lombok.Getter;

/**
 * Response whenever we have an error
 */
@Getter
public class ErrorResponse {

    public ErrorResponse(String message) {
        this.message = message;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    private final String message;
    private final Timestamp timestamp;
}
