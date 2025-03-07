package com.movie.app.controller;

import com.movie.app.dto.MovieDetailDTO;
import com.movie.app.dto.MovieSummaryDTO;
import com.movie.app.exception.InvalidRequestException;
import com.movie.app.exception.MovieNotFoundException;
import com.movie.app.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

  private static final Logger logger = LoggerFactory.getLogger(MovieController.class);  // Logger instance

  private final MovieService movieService;

  @Autowired
  public MovieController(MovieService movieService) {
    this.movieService = movieService;
  }

  /**
   * Fetches popular movies based on the given pagination parameters.
   * If invalid pagination parameters are provided (negative values), it throws an InvalidRequestException.
   *
   * @param page the page number to fetch (default is 0)
   * @param size the number of movies per page (default is 50)
   * @return a ResponseEntity containing a page of MovieSummaryDTOs
   * @throws InvalidRequestException if the page or size are negative
   */
  @GetMapping("/popular")
  public ResponseEntity<Page<MovieSummaryDTO>> getPopularMovies(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "50") int size) {

    // Validate input for page and size
    if (page < 0 || size <= 0) {
      logger.error("Invalid page or size: page={}, size={}", page, size);
      throw new InvalidRequestException("Page number must be non-negative and size must be positive.");
    }

    logger.info("Received request to fetch popular movies with page={} and size={}", page, size);
    Pageable pageable = PageRequest.of(page, size, Sort.by("rating").descending());
    return ResponseEntity.ok(movieService.getPopularMovies(pageable));
  }

  /**
   * Searches for movies based on the given query. If the query is empty or null, it throws an InvalidRequestException.
   *
   * @param query the search query
   * @return a ResponseEntity containing a list of MovieSummaryDTOs
   * @throws InvalidRequestException if the query is empty or null
   */
  @GetMapping("/search")
  public ResponseEntity<List<MovieSummaryDTO>> searchMovies(@RequestParam String query) {
    if (query == null || query.trim().isEmpty()) {
      logger.error("Invalid search query: query is empty or null");
      throw new InvalidRequestException("Search query cannot be empty or null.");
    }

    logger.info("Received request to search movies with query: {}", query);
    return ResponseEntity.ok(movieService.searchMovies(query));
  }

  /**
   * Fetches movie details by movie ID. If the movie is not found, a MovieNotFoundException is thrown.
   *
   * @param id the ID of the movie
   * @return a ResponseEntity containing the MovieDetailDTO
   * @throws MovieNotFoundException if the movie with the given ID is not found
   */
  @GetMapping("/{id}")
  public ResponseEntity<MovieDetailDTO> getMovieById(@PathVariable Long id) {
    if (id <= 0) {
      logger.error("Invalid movie ID: {}", id);
      throw new InvalidRequestException("Movie ID must be a positive number.");
    }

    logger.info("Received request to fetch movie details for movie ID: {}", id);
    return ResponseEntity.ok(movieService.getMovieById(id));
  }
}