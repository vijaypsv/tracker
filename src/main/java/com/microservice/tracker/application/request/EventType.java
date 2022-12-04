package com.microservice.tracker.application.request;

import io.swagger.annotations.ApiModel;

// TODO check with business event types. Based on that we may leave it as an enum or move to a database entity
/**
 * Types of events
 */
@ApiModel
public enum EventType {
    LOGIN, LOGOUT, QUERY_ENTITY, CREATE_ENTITY, UPDATE_ENTITY, DELETE_ENTITY
}
