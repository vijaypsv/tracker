package com.microservice.tracker.domain.service;

import java.util.List;
import java.util.UUID;

import com.microservice.tracker.domain.SessionNotFoundException;
import com.microservice.tracker.domain.model.Event;
import com.microservice.tracker.domain.model.Session;
import com.microservice.tracker.domain.repository.SessionRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Session business logic
 */
@Slf4j
public class SessionService {
    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    /**
     * Gets the list of all the sessions
     *
     * @return list of {@link Session}
     */
    public List<Session> getSessions() {
        log.debug("Called get sessions");
        return sessionRepository.findAll();
    }

    /**
     * Retrieves a session by id
     *
     * @param sessionId session id
     * @return {@link Session} retrieved
     */
    public Session getSession(UUID sessionId) {
        log.debug("Called get session with UUID {}", sessionId);
        return sessionRepository
            .findById(sessionId)
            .orElseThrow(() -> new SessionNotFoundException("Session doesn't exist"));
    }

    /**
     * Creates a session
     *
     * @param session {@link Session} to create
     * @return UUID representing the session id
     */
    public UUID createSession(Session session) {
        session.setSessionId(UUID.randomUUID());
        sessionRepository.save(session);
        log.info("User {} started session at {}", session.getUserId(), session.getStartAt());
        return session.getSessionId();
    }

    /**
     * Adds multiple {@link Event} to a session.
     *
     * @param sessionId session id
     * @param events list of {@link Event} to add
     */
    public void addEvents(UUID sessionId, List<Event> events) {
        final Session session = getSession(sessionId);
        events.forEach(session::addEvent);
        sessionRepository.save(session);
        log.info("Added events to session {}", sessionId);
    }

    /**
     * Adds an {@link Event} to a session.
     *
     * @param sessionId session id
     * @param event {@link Event} to add
     */
    public void addEvent(UUID sessionId, Event event) {
        final Session session = getSession(sessionId);
        session.addEvent(event);
        sessionRepository.save(session);
        log.info("Added event to session {} at {}", sessionId, event.getEventAt());
    }

    /**
     * Ends a session, indicating the end time.
     * TODO aks if the endAt parameter is required. We can put the time this method is called instead
     *
     * @param sessionId session id
     * @param endAt time of the end of the sesion in milliseconds
     */
    public void endSession(UUID sessionId, Long endAt) {
        final Session session = getSession(sessionId);
        session.endSession(endAt);
        sessionRepository.save(session);
        log.info("Finished session {} at {}", sessionId, endAt);
    }
}
