package com.microservice.tracker.application.validator;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Pattern;

/**
 * Custom validator that checks if a string is an uuid
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy={})
@Retention(RUNTIME)
@Pattern(regexp="^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$", flags=Pattern.Flag.CASE_INSENSITIVE)
@ReportAsSingleViolation
public @interface Uuid {
    String message() default "{invalid.uuid}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
