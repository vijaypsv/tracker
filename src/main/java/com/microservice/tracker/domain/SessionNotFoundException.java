package com.microservice.tracker.domain;

/**
 * Generic domain exception
 */
public class SessionNotFoundException extends RuntimeException {
    public SessionNotFoundException(final String message) {
        super(message);
    }
}