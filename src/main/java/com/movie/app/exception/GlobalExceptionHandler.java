package com.movie.app.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler to manage application-wide exceptions.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);  // Logger instance

  /**
   * Handles InvalidRequestException, which occurs due to invalid input parameters.
   * Logs the error message and returns a structured error response with 400 status.
   *
   * @param ex the InvalidRequestException that was thrown
   * @return a ResponseEntity containing a structured error response with 400 status
   */
  @ExceptionHandler(InvalidRequestException.class)
  public ResponseEntity<Map<String, Object>> handleInvalidRequestException(InvalidRequestException ex) {
    logger.error("Invalid request: {}", ex.getMessage());
    return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  /**
   * Handles MovieNotFoundException when a movie with the specified ID is not found.
   * Logs the error message and returns a structured error response with 404 status.
   *
   * @param ex the MovieNotFoundException that was thrown
   * @return a ResponseEntity containing a structured error response with 404 status
   */
  @ExceptionHandler(MovieNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handleMovieNotFoundException(MovieNotFoundException ex) {
    logger.error("Movie not found: {}", ex.getMessage());
    return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  /**
   * Handles generic exceptions, logging the error and returning a 500 status code.
   *
   * @param ex the generic Exception
   * @return a ResponseEntity containing a structured error response with 500 status
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
    logger.error("Unexpected error occurred: {}", ex.getMessage(), ex);
    return buildErrorResponse("An unexpected error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * Builds a structured error response with error details.
   *
   * @param message the error message
   * @param status the HTTP status code
   * @return a ResponseEntity containing the structured error response
   */
  private ResponseEntity<Map<String, Object>> buildErrorResponse(String message, HttpStatus status) {
    Map<String, Object> errorResponse = new HashMap<>();
    errorResponse.put("timestamp", LocalDateTime.now());
    errorResponse.put("status", status.value());
    errorResponse.put("error", status.getReasonPhrase());
    errorResponse.put("message", message);
    return new ResponseEntity<>(errorResponse, status);
  }
}