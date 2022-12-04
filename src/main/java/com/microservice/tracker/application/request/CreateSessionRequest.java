package com.microservice.tracker.application.request;

import java.util.UUID;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.microservice.tracker.application.validator.Uuid;
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
    @NotNull (message = "userId required")
    @Uuid(message = "invalid userId")
    private String userId;
    @NotNull (message = "machineId required")
    @Uuid (message = "invalid machineId")
    private String machineId;
    @NotNull (message = "orgId required")
    @Min(value = 0L, message = "incorrect orgId")
    private Long orgId; //TODO Consult with business if we need a validation for this field
    @NotNull (message = "startAt required")
    @Min(value = 946684800000L, message = "incorrect timestamp for startAt")
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
