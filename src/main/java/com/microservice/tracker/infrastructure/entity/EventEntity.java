package com.microservice.tracker.infrastructure.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.microservice.tracker.domain.model.Event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "event_at")
    private Timestamp eventAt;

    @Column(name = "event_type")
    private String eventType;

    @Column(name = "payload")
    private String payload;

    //@ManyToOne
    //@JoinColumn(name="session_id", nullable=false)
    @ManyToOne
    @JoinColumn(name = "session_id", insertable = false, updatable = false)
    private SessionEntity session;

    public EventEntity (Event event, SessionEntity session) {
        this.id = event.getId();
        this.eventAt = new Timestamp(event.getEventAt());
        this.eventType = event.getEventType();
        this.payload = event.getPayload();
        this.session = session;
    }

    public Event toEvent() {
        return Event.builder()
                    .id(this.id)
                    .eventAt(this.eventAt.getTime())
                    .eventType(this.eventType)
                    .payload(this.payload)
                    .build();
    }
}
