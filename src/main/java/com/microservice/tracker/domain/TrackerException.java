package com.microservice.tracker.domain;

/**
 * Generic domain exception
 */
public class TrackerException extends RuntimeException {
    public TrackerException(final String message) {
        super(message);
    }
}