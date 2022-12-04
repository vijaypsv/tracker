package com.microservice.tracker.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.microservice.tracker.domain.TrackerException;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Entity to store sessions
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Session {
    private UUID sessionId;
    private UUID userId; // TODO: maybe this references another table that we should have? Need more info
    private UUID machineId; // TODO: maybe this references another table that we should have? Need more info
    private Long orgId;
    private Long startAt;
    @Setter(AccessLevel.NONE)
    private Long endAt;
    @Setter(AccessLevel.NONE)
    private List<Event> sessionEvents;

    /**
     * Adds an {@link Event} to the session. Checks if the session is still active
     *
     * @param event {@link Event} to add
     */
    public void addEvent(final Event event) {
        validateCanAddEvent(event);
        if (this.sessionEvents == null) {
            this.sessionEvents = new ArrayList<>();
        }
        this.sessionEvents.add(event);
    }

    /**
     * Ends a session, indicating the end time.
     * TODO aks if the endAt parameter is required. We can put the time this method is called instead
     *
     * @param endAt time of the end of the sesion in milliseconds
     */
    public void endSession(Long endAt) {
        validateCanEndSession();
        this.endAt = endAt;
    }

    /**
     * Validates if we can add an event
     */
    private void validateCanAddEvent(final Event event) {
        if (event == null || event.getEventAt() == null) {
            throw new TrackerException("Invalid event");
        }

        if (this.endAt != null && this.endAt < event.getEventAt()) { // we don't allow events after the sesion ended
            throw new TrackerException("Cannot modify a finished session");
        }
    }

    /**
     * Validates if a session can be ended
     */
    private void validateCanEndSession() {
        if (this.endAt != null) {
            throw new TrackerException("Cannot modify a finished session");
        }
    }
}
