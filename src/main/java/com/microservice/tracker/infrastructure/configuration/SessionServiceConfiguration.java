package com.microservice.tracker.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.microservice.tracker.TrackerApplication;
import com.microservice.tracker.domain.repository.SessionRepository;
import com.microservice.tracker.domain.service.SessionService;

@Configuration
@ComponentScan(basePackageClasses = TrackerApplication.class)
public class SessionServiceConfiguration {

    @Bean
    SessionService sessionService(SessionRepository sessionRepository) {
        return new SessionService(sessionRepository);
    }
}
