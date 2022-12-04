package com.microservice.tracker.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity to store session events
 */
@Getter
@Setter
@Builder
public class Event {
    private Long id;
    private Long eventAt;
    private String eventType;
    private String payload;
}
