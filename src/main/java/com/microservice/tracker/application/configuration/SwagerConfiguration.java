package com.microservice.tracker.application.configuration;

import static org.springframework.http.HttpMethod.GET;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.Response;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwagerConfiguration {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.microservice.tracker.application.controller"))
            .paths(PathSelectors.any())
            .build()
            .useDefaultResponseMessages(false)
            .globalResponses(GET, buildResponsesDoc());
    }

    private ArrayList<Response> buildResponsesDoc() {
        ArrayList<Response> responses = new ArrayList<>();
        responses.add(new ResponseBuilder()
            .code("400")
            .description("Error with the attributes or parameters. This may happen if we have asked for invalid or empty values")
            .build());
        responses.add(new ResponseBuilder()
            .code("404")
            .description("Session not found")
            .build());
        responses.add(new ResponseBuilder()
            .code("500")
            .description("Server error")
            .build());
        return responses;
    }
}
