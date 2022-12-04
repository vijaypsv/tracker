package com.microservice.tracker.application.request;

import java.util.UUID;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.microservice.tracker.application.validator.Uuid;
import com.microservice.tracker.domain.model.Session;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(dataType = "java.util.UUID")
    private String userId;
    @NotNull (message = "machineId required")
    @Uuid (message = "invalid machineId")
    @ApiModelProperty(dataType = "java.util.UUID")
    private String machineId;
    @NotNull (message = "orgId required")
    @Min(value = 0L, message = "incorrect orgId")
    @ApiModelProperty(example = "1")
    private Long orgId; //TODO Consult with business if we need a validation for this field
    @NotNull (message = "startAt required")
    @Min(value = 946684800000L, message = "incorrect timestamp for startAt. It should represent a date higher than 01/01/2000") // TODO ask business if there is a date limitation
    @ApiModelProperty(example = "946684800000")
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
