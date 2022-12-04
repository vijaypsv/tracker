package com.microservice.tracker.application.request;

import java.util.UUID;

import com.microservice.tracker.domain.model.Session;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Request for creating a session
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateSessionRequest {
    private String userId;
    private String machineId;
    private Long orgId; //TODO Consult with business if we need a validation for this field

    private Long startAt;
    public Session getSession(){
        return Session.builder()
            .userId(UUID.fromString(userId))
            .machineId(UUID.fromString(machineId))
            .orgId(orgId)
            .startAt(startAt)
            .build();
    }
}
