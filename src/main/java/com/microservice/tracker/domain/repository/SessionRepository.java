package com.microservice.tracker.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.microservice.tracker.domain.model.Session;

/**
 * Contains all the methods to persist a session
 */
public interface SessionRepository {

    /**
     * Gets the list of all the sessions
     *
     * @return list of {@link Session}
     */
    List<Session> findAll();

    /**
     * Retrieves a session by id
     *
     * @param id session id
     * @return {@link Session} retrieved
     */
    Optional<Session> findById(UUID id);

    /**
     * Persist a session
     *
     * @param session {@link Session} to persist
     */
    void save(Session session);
}
