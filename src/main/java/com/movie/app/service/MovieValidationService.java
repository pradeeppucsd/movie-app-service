package com.movie.app.service;

import com.movie.app.exception.InvalidRequestException;
import org.springframework.stereotype.Service;

/**
 * Service responsible for validating input parameters.
 */
@Service
public class MovieValidationService {

  /**
   * Validates the page and size parameters.
   *
   * @param page the page number
   * @param size the size of the page
   * @throws InvalidRequestException if the page or size are invalid
   */
  public void validatePageAndSize(int page, int size) {
    if (page < 0 || size <= 0) {
      throw new InvalidRequestException("Page number must be non-negative and size must be positive.");
    }
  }

  /**
   * Validates the search query.
   *
   * @param query the search query
   * @throws InvalidRequestException if the query is empty or null
   */
  public void validateSearchQuery(String query) {
    if (query == null || query.trim().isEmpty()) {
      throw new InvalidRequestException("Search query cannot be empty or null.");
    }
  }

  /**
   * Validates the movie ID.
   *
   * @param id the movie ID
   * @throws InvalidRequestException if the movie ID is invalid
   */
  public void validateMovieId(Long id) {
    if (id <= 0) {
      throw new InvalidRequestException("Movie ID must be a positive number.");
    }
  }
}