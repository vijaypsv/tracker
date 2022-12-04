package com.microservice.tracker.application.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.microservice.tracker.domain.model.Event;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Request for adding an event
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddEventRequest {
    @NotNull (message = "eventAt required")
    @Min(value = 946684800000L, message = "incorrect timestamp for eventAt")
    @ApiModelProperty(example = "946684800000")
    private Long eventAt;
    @NotNull (message = "eventType required")
    private EventType eventType;
    @NotNull (message = "payload required")
    private String payload; // TODO make this a json

    public Event getEvent(){
        return Event.builder()
            .eventAt(eventAt)
            .eventType(eventType.toString())
            .payload(payload)
            .build();
    }
}
