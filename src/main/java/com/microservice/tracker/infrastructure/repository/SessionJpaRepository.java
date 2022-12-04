package com.microservice.tracker.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.tracker.infrastructure.entity.SessionEntity;

public interface SessionJpaRepository extends JpaRepository<SessionEntity, UUID> {
}
