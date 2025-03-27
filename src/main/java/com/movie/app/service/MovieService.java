package com.movie.app.service;

import com.movie.app.dto.MovieDetailDTO;
import com.movie.app.dto.MovieSummaryDTO;
import com.movie.app.dto.PaginatedResponse;
import com.movie.app.entity.Movie;
import com.movie.app.exception.MovieNotFoundException;
import com.movie.app.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class that handles business logic related to movies.
 * Provides methods to fetch popular movies, search movies by title,
 * and retrieve detailed information about a specific movie.
 */
@Service
public class MovieService {

  private static final Logger logger = LoggerFactory.getLogger(MovieService.class);  // Logger instance

  private final MovieRepository movieRepository;

  /**
   * Constructs a MovieService with the specified MovieRepository.
   *
   * @param movieRepository the repository responsible for accessing movie data
   */
  @Autowired
  public MovieService(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  /**
   * Retrieves a paginated list of popular movies.
   *
   * @param pageable the pagination information (page number and size)
   * @return a Page containing MovieSummaryDTO objects representing the popular movies
   */
  public PaginatedResponse<MovieSummaryDTO> getPopularMovies(Pageable pageable) {
    logger.info("Fetching popular movies with pagination: page={}, size={}",
        pageable.getPageNumber(), pageable.getPageSize());

    Page<Movie> moviePage = movieRepository.findAll(pageable);

    // Return empty list for empty pages
    if (moviePage.getContent().isEmpty()) {
      logger.warn("No popular movies found for the requested page: page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
    }

    return new PaginatedResponse<>(
        moviePage.getContent().stream().map(movie -> new MovieSummaryDTO(
            movie.getId(),
            movie.getTitle(),
            movie.getReleaseDate(),
            movie.getPosterUrl(),
            movie.getRating(),
            movie.getPopularity()
        )).toList(),
        moviePage.getNumber(),
        moviePage.getTotalPages(),
        moviePage.getTotalElements()
    );
  }

  /**
   * Searches for movies based on the provided query string.
   *
   * @param query the search query string to search movie titles
   * @return a List of MovieSummaryDTO objects matching the search query
   * @throws MovieNotFoundException if no movies are found matching the query
   */
  public List<MovieSummaryDTO> searchMovies(String query) {
    logger.info("Searching for movies with query: {}", query);
    List<Movie> movies = movieRepository.findByTitleContainingIgnoreCase(query);
    if (movies.isEmpty()) {
      logger.warn("No movies found matching query: {}", query);
      throw new MovieNotFoundException("No movies found matching query: " + query);
    }
    return movies.stream()
        .map(movie -> new MovieSummaryDTO(
            movie.getId(),
            movie.getTitle(),
            movie.getReleaseDate(),
            movie.getPosterUrl(),
            movie.getRating(),
            movie.getPopularity()
        ))
        .collect(Collectors.toList());
  }

  /**
   * Retrieves the details of a specific movie by its ID.
   *
   * @param id the ID of the movie
   * @return a MovieDetailDTO object containing detailed information of the movie
   * @throws MovieNotFoundException if the movie with the given ID is not found
   */
  public MovieDetailDTO getMovieById(Long id) {
    logger.info("Fetching movie details for movie ID: {}", id);
    Movie movie = movieRepository.findById(id)
        .orElseThrow(() -> {
          logger.error("Movie with ID {} not found.", id);
          return new MovieNotFoundException("Movie with ID " + id + " not found.");
        });

    return new MovieDetailDTO(
        movie.getTitle(),
        movie.getReleaseDate(),
        movie.getPosterUrl(),
        movie.getOverview(),
        movie.getGenres(),
        movie.getRating(),
        movie.getRuntime(),
        movie.getLanguage()
    );
  }
}
