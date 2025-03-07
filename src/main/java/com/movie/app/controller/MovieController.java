package com.movie.app.controller;

import com.movie.app.dto.MovieDetailDTO;
import com.movie.app.dto.MovieSummaryDTO;
import com.movie.app.service.MovieService;
import com.movie.app.service.MovieValidationService;
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
  private final MovieValidationService movieValidationService;

  @Autowired
  public MovieController(MovieService movieService, MovieValidationService movieValidationService) {
    this.movieService = movieService;
    this.movieValidationService = movieValidationService;
  }

  @GetMapping("/popular")
  public ResponseEntity<Page<MovieSummaryDTO>> getPopularMovies(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "50") int size) {

    movieValidationService.validatePageAndSize(page, size);

    logger.info("Received request to fetch popular movies with page={} and size={}", page, size);
    Pageable pageable = PageRequest.of(page, size, Sort.by("rating").descending());
    return ResponseEntity.ok(movieService.getPopularMovies(pageable));
  }

  @GetMapping("/search")
  public ResponseEntity<List<MovieSummaryDTO>> searchMovies(@RequestParam String query) {
    movieValidationService.validateSearchQuery(query);

    logger.info("Received request to search movies with query: {}", query);
    return ResponseEntity.ok(movieService.searchMovies(query));
  }

  @GetMapping("/{id}")
  public ResponseEntity<MovieDetailDTO> getMovieById(@PathVariable Long id) {
    movieValidationService.validateMovieId(id);

    logger.info("Received request to fetch movie details for movie ID: {}", id);
    return ResponseEntity.ok(movieService.getMovieById(id));
  }
}
