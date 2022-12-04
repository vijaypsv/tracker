package com.microservice.tracker.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.microservice.tracker.domain.model.Session;
import com.microservice.tracker.domain.repository.SessionRepository;
import com.microservice.tracker.infrastructure.entity.SessionEntity;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class H2SessionRepository implements SessionRepository {

    private final SessionJpaRepository sessionJpaRepository;

    @Autowired
    public H2SessionRepository(SessionJpaRepository sessionJpaRepository) {
        this.sessionJpaRepository = sessionJpaRepository;
    }

    @Override
    public Optional<Session> findById(UUID id) {
        log.debug("H2SessionRepository -> findById: {}", id);
        Optional<SessionEntity> session = sessionJpaRepository.findById(id);
        return session.map(SessionEntity::toSession);
    }

    @Override
    public void save(Session session) {
        log.debug("H2SessionRepository -> save: {}", session.getSessionId());
        sessionJpaRepository.save(new SessionEntity(session));
    }

    @Override
    public List<Session> findAll() {
        log.debug("H2SessionRepository -> findAll");
        return sessionJpaRepository
            .findAll()
            .stream()
            .map(SessionEntity::toSession)
            .collect(Collectors.toList());
    }
}
