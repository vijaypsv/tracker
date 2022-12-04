package com.microservice.tracker.application;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.microservice.tracker.application.response.ErrorResponse;
import com.microservice.tracker.domain.SessionNotFoundException;

import lombok.extern.slf4j.Slf4j;

/**
 * Handles all of the error responses of the application
 */
@Slf4j
@ControllerAdvice
public class TrackerExceptionHandler {

    /**
     * Method to manage not found exceptions
     *
     * @param e exception received
     * @return Error response
     */
    @ExceptionHandler(SessionNotFoundException.class)
    public ResponseEntity<ErrorResponse> catchEntityNotFoundException(SessionNotFoundException e) {
        log.error(e.getMessage(), e);
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Method to manage exceptions related to the request
     *
     * @param e exception received
     * @return Error response
     */
    @ExceptionHandler({MissingServletRequestParameterException.class, MultipartException.class})
    public ResponseEntity<ErrorResponse> catchWrongRequestExceptions(Exception e) {
        log.error(e.getMessage(), e);
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Method to manage exceptions related incorrect parameters
     *
     * @param e exception received
     * @return Error response
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> catchMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error(e.getMessage(), e);
        ErrorResponse response = new ErrorResponse("Invalid parameters:" + e.getValue());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Method to manage invalid parameters
     *
     * @param e exception received
     * @return Error response
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> catchMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage(); // TODO maybe clean up this??
        log.error(message, e);
        ErrorResponse response = new ErrorResponse(message);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Method to manage invalid parameters
     *
     * @param e exception received
     * @return Error response
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> catchHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        String field = ((InvalidFormatException) e.getCause()).getPath().get(0).getFieldName(); // TODO maybe clean up this and check cause exception type??
        String message = String.format("invalid %s", field);
        log.error(message, e);
        ErrorResponse response = new ErrorResponse(message);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Method to manage uncaught exceptions.
     *
     * @param e exception received
     * @return Error response
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> catchException(Exception e) {
        log.error(e.getMessage(), e);
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
