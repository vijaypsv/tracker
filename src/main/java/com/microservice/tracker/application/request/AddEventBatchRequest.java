package com.microservice.tracker.application.request;

import java.util.List;
import java.util.stream.Collectors;

import com.microservice.tracker.domain.model.Event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Request for adding an event
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddEventBatchRequest {

    List<AddEventRequest> events;

    public List<Event> getEvents() {
        return events.stream().map(AddEventRequest::getEvent).collect(Collectors.toList());
    }
}
