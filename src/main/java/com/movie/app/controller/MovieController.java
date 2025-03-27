package com.movie.app.controller;

import com.movie.app.dto.MovieDetailDTO;
import com.movie.app.dto.MovieSummaryDTO;
import com.movie.app.dto.PaginatedResponse;
import com.movie.app.service.MovieService;
import com.movie.app.service.MovieValidationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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

/**
 * Controller class that handles requests related to movies. Provides endpoints to fetch popular
 * movies, search for movies, and get movie details by ID.
 */
@RestController
@RequestMapping("/movies")
@Tag(name = "Movies", description = "Endpoints for retrieving movie data")
public class MovieController {

  private static final Logger logger = LoggerFactory.getLogger(
      MovieController.class);  // Logger instance

  private final MovieService movieService;
  private final MovieValidationService movieValidationService;

  /**
   * Constructs a MovieController with the specified MovieService and MovieValidationService.
   *
   * @param movieService           the service responsible for handling movie data
   * @param movieValidationService the service responsible for validating movie-related requests
   */
  @Autowired
  public MovieController(MovieService movieService, MovieValidationService movieValidationService) {
    this.movieService = movieService;
    this.movieValidationService = movieValidationService;
  }

  /**
   * Retrieves a paginated list of popular movies based on their ratings.
   *
   * @param page the page number to retrieve (default is 0)
   * @param size the number of movies per page (default is 50)
   * @return a ResponseEntity containing a paginated list of MovieSummaryDTO objects
   */
  @GetMapping("/popular")
  @Operation(
      summary = "Get popular movies",
      description = "Retrieves a paginated list of popular movies based on their ratings."
  )
  public ResponseEntity<PaginatedResponse<MovieSummaryDTO>> getPopularMovies(
      @RequestParam(defaultValue = "0")
      @Parameter(description = "Page number (default is 0)") int page,
      @RequestParam(defaultValue = "50")
      @Parameter(description = "Number of movies per page (default is 50)") int size) {

    movieValidationService.validatePageAndSize(page, size);

    logger.info("Received request to fetch popular movies with page={} and size={}", page, size);
    Pageable pageable = PageRequest.of(page, size, Sort.by("popularity").descending());
    return ResponseEntity.ok(movieService.getPopularMovies(pageable));
  }

  /**
   * Searches for movies based on the provided query string.
   *
   * @param query the search query string
   * @return a ResponseEntity containing a list of MovieSummaryDTO objects matching the query
   */
  @GetMapping("/search")
  @Operation(
      summary = "Search movies",
      description = "Searches for movies based on the provided query string."
  )
  public ResponseEntity<List<MovieSummaryDTO>> searchMovies(@RequestParam
  @Parameter(description = "Search query for movie title or keywords") String query) {
    movieValidationService.validateSearchQuery(query);

    logger.info("Received request to search movies with query: {}", query);
    return ResponseEntity.ok(movieService.searchMovies(query));
  }

  /**
   * Retrieves the details of a specific movie by its ID.
   *
   * @param id the ID of the movie
   * @return a ResponseEntity containing the MovieDetailDTO object for the specified movie
   */
  @GetMapping("/{id}")
  @Operation(
      summary = "Get movie details",
      description = "Retrieves the details of a specific movie by its ID."
  )
  public ResponseEntity<MovieDetailDTO> getMovieById(@PathVariable
  @Parameter(description = "ID of the movie to retrieve") Long id) {
    movieValidationService.validateMovieId(id);

    logger.info("Received request to fetch movie details for movie ID: {}", id);
    return ResponseEntity.ok(movieService.getMovieById(id));
  }
}
