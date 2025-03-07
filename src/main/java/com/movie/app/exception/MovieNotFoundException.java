package com.movie.app.exception;

/**
 * Custom exception thrown when a movie is not found.
 */
public class MovieNotFoundException extends RuntimeException {

  /**
   * Constructor to create a new MovieNotFoundException with a custom message.
   *
   * @param message the message describing the exception
   */
  public MovieNotFoundException(String message) {
    super(message);
  }
}