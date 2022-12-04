package com.microservice.tracker.domain.model;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertNull;
import static com.microservice.tracker.domain.TrackerMockProvider.getEvent;
import static com.microservice.tracker.domain.TrackerMockProvider.getEventInvalidEventAt;
import static com.microservice.tracker.domain.TrackerMockProvider.getFinishedSession;
import static com.microservice.tracker.domain.TrackerMockProvider.getSession;

import org.testng.annotations.Test;

import com.microservice.tracker.domain.TrackerException;

public class SessionTest {

    @Test
    public void testAddEvent () {
        Session session = getSession();

        Event event = getEvent();

        session.addEvent(event);

        assertNotNull(session.getSessionEvents());
        assertEquals(1, session.getSessionEvents().size());
    }

    @Test
    public void testAddEventFinishedSession () {
        Session session = getFinishedSession();
        Event event = getEvent();

        session.addEvent(event);

        assertNotNull(session.getSessionEvents());
        assertEquals(1, session.getSessionEvents().size());
    }

    @Test(expectedExceptions = TrackerException.class)
    public void testAddEventFinishedSessionError () {
        Session session = getFinishedSession();
        Event event = getEventInvalidEventAt();

        session.addEvent(event);

        assertNull(session.getSessionEvents());
    }

    @Test
    public void testEndSession () {
        Session session = getSession();

        session.endSession(1111L);

        assertNotNull(session.getEndAt());
    }

    @Test(expectedExceptions = TrackerException.class)
    public void testEndSessionFinishedSession () {
        Session session = getFinishedSession();

        session.endSession(1111L);
    }
}
