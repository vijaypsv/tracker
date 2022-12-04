package com.microservice.tracker.application.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Response after creating a session
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateSessionResponse {
    @ApiModelProperty(dataType = "java.util.UUID")
    private String sessionId;
}
