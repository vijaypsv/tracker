package com.microservice.tracker.infrastructure.entity;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.microservice.tracker.domain.model.Event;
import com.microservice.tracker.domain.model.Session;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class SessionEntity {
    @Id
    @Column(name = "session_id", nullable = false)
    private UUID sessionId;

    @Column(name = "user_id")
    private UUID userId; // TODO: maybe this references another table that we should have? Need more info

    @Column(name = "machine_id")
    private UUID machineId; // TODO: maybe this references another table that we should have? Need more info

    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "start_at")
    private Timestamp startAt;

    @Column(name = "end_at")
    private Timestamp endAt;

    //@OneToMany(mappedBy="session")
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "session_id")
    private List<EventEntity> sessionEvents;

    public SessionEntity(Session session) {
        this.sessionId = session.getSessionId();
        this.userId = session.getUserId();
        this.machineId = session.getMachineId();
        this.orgId = session.getOrgId();
        this.startAt = new Timestamp(session.getStartAt());

        if (session.getEndAt() != null) this.endAt = new Timestamp(session.getEndAt());

        if (session.getSessionEvents() != null) {
            this.sessionEvents = session.getSessionEvents()
                                        .stream()
                                        .map(e -> new EventEntity(e, this))
                                        .collect(Collectors.toList());
        }
    }

    public Session toSession() {
        List<Event> events = this.sessionEvents.stream()
                                                   .map(EventEntity::toEvent)
                                                   .collect(Collectors.toList());
        return Session.builder()
            .sessionId(sessionId)
            .userId(userId)
            .machineId(machineId)
            .orgId(orgId)
            .sessionEvents(events)
            .startAt(startAt.getTime())
            .endAt(endAt!=null ? endAt.getTime() : null)
            .build();
    }
}
