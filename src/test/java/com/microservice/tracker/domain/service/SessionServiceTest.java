package com.microservice.tracker.domain.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertNull;
import static com.microservice.tracker.domain.TrackerMockProvider.getEmptyEvent;
import static com.microservice.tracker.domain.TrackerMockProvider.getEvent;
import static com.microservice.tracker.domain.TrackerMockProvider.getEventInvalidEventAt;
import static com.microservice.tracker.domain.TrackerMockProvider.getFinishedSession;
import static com.microservice.tracker.domain.TrackerMockProvider.getSession;

import java.util.Optional;
import java.util.UUID;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.microservice.tracker.domain.SessionNotFoundException;
import com.microservice.tracker.domain.TrackerException;
import com.microservice.tracker.domain.model.Event;
import com.microservice.tracker.domain.model.Session;
import com.microservice.tracker.domain.repository.SessionRepository;

public class SessionServiceTest {

    @InjectMocks
    private SessionService sessionService;
    @Mock
    private SessionRepository sessionRepository;
    private AutoCloseable closeable;

    @BeforeSuite
    public void setupSuite() {
        closeable = openMocks(this);
    }

    @AfterSuite
    void closeService() throws Exception {
        closeable.close();
    }

    @BeforeMethod
    public void setup() {
        reset(sessionRepository);
    }

    @Test(expectedExceptions = SessionNotFoundException.class)
    public void testGetSessionNotfound () {
        sessionService.getSession(UUID.randomUUID());
    }

    @Test
    public void testGetSession() {
        UUID sessionId = UUID.randomUUID();
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(new Session()));

        Session session = sessionService.getSession(sessionId);

        assertNotNull(session);
        verify(sessionRepository, times(1)).findById(sessionId);
    }

    @Test
    public void testCreateSession () {
        Session session = new Session();

        UUID sessionId = sessionService.createSession(session);

        assertNotNull(sessionId);
        verify(sessionRepository, times(1)).save(any());
    }

    @Test
    public void testAddEvent () {
        Session session = getSession();
        when(sessionRepository.findById(session.getSessionId())).thenReturn(Optional.of(session));
        Event event = getEvent();

        sessionService.addEvent(session.getSessionId(), event);

        assertNotNull(session.getSessionEvents());
        assertEquals(1, session.getSessionEvents().size());
        verify(sessionRepository, times(1)).save(any());
    }

    @Test
    public void testAddEventToFinishedSession () {
        Session session = getFinishedSession();
        when(sessionRepository.findById(session.getSessionId())).thenReturn(Optional.of(session));
        Event event = getEvent();

        sessionService.addEvent(session.getSessionId(), event);

        assertNotNull(session.getSessionEvents());
        assertEquals(1, session.getSessionEvents().size());
        verify(sessionRepository, times(1)).save(any());
    }

    @Test(expectedExceptions = TrackerException.class)
    public void testAddInvalidEvent () {
        Session session = getFinishedSession();
        when(sessionRepository.findById(session.getSessionId())).thenReturn(Optional.of(session));
        Event event = getEmptyEvent();

        sessionService.addEvent(session.getSessionId(), event);

        assertNull(session.getSessionEvents());
        verify(sessionRepository, never()).save(any());
    }

    @Test(expectedExceptions = TrackerException.class)
    public void testAddNullEvent () {
        Session session = getFinishedSession();
        when(sessionRepository.findById(session.getSessionId())).thenReturn(Optional.of(session));

        sessionService.addEvent(session.getSessionId(), null);

        assertNull(session.getSessionEvents());
        verify(sessionRepository, never()).save(any());
    }

    @Test(expectedExceptions = TrackerException.class)
    public void testAddEventToFinishedSessionError () {
        Session session = getFinishedSession();
        when(sessionRepository.findById(session.getSessionId())).thenReturn(Optional.of(session));
        Event event = getEventInvalidEventAt();

        sessionService.addEvent(session.getSessionId(), event);

        assertNull(session.getSessionEvents());
        verify(sessionRepository, never()).save(any());
    }

    @Test
    public void testEndSession () {
        Session session = getSession();
        when(sessionRepository.findById(session.getSessionId())).thenReturn(Optional.of(session));

        sessionService.endSession(session.getSessionId(), 1111L);

        assertNotNull(session.getEndAt());
        verify(sessionRepository, times(1)).save(any());
    }

    @Test(expectedExceptions = TrackerException.class)
    public void testEndSessionFinishedSession () {
        Session session = getFinishedSession();
        when(sessionRepository.findById(session.getSessionId())).thenReturn(Optional.of(session));

        sessionService.endSession(session.getSessionId(), 1111L);

        verify(sessionRepository, never()).save(any());
    }
}
