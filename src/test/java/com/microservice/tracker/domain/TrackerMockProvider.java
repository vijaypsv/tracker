package com.microservice.tracker.domain;

import java.util.UUID;

import com.microservice.tracker.domain.model.Event;
import com.microservice.tracker.domain.model.Session;

public class TrackerMockProvider {
    public static Session getSession() {
        return Session.builder().sessionId(UUID.randomUUID()).build();
    }

    public static Session getFinishedSession() {
        return Session.builder().sessionId(UUID.randomUUID()).endAt(1111L).build();
    }

    public static Event getEvent() {
        return Event.builder().eventAt(111L).build();
    }

    public static Event getEventInvalidEventAt() {
        return Event.builder().eventAt(2222L).build();
    }

    public static Event getEmptyEvent() {
        Event event = Event.builder().build();
        return event;
    }
}
