package com.microservice.tracker.application.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Request for finishing a session
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EndSessionRequest {
    @NotNull (message = "endtAt required")
    @Min(value = 946684800000L, message = "incorrect timestamp for endtAt. It should represent a date higher than 01/01/2000") // TODO ask business if there is a date limitation
    @ApiModelProperty(example = "946684800000")
    private Long endtAt;
}
