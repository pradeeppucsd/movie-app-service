package com.movie.app.exception;

/**
 * Exception thrown when an invalid request is made, such as invalid input parameters.
 */
public class InvalidRequestException extends RuntimeException {

  public InvalidRequestException(String message) {
    super(message);
  }
}