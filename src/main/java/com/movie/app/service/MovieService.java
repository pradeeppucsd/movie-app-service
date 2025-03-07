package com.movie.app.service;

import com.movie.app.dto.MovieDetailDTO;
import com.movie.app.dto.MovieSummaryDTO;
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

@Service
public class MovieService {

  private static final Logger logger = LoggerFactory.getLogger(MovieService.class);  // Logger instance

  private MovieRepository movieRepository;

  @Autowired
  public MovieService(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  public Page<MovieSummaryDTO> getPopularMovies(Pageable pageable) {
    logger.info("Fetching popular movies with pagination: page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
    return movieRepository.findAll(pageable)
        .map(movie -> new MovieSummaryDTO(
            movie.getId(),
            movie.getTitle(),
            movie.getReleaseDate(),
            movie.getPosterUrl(),
            movie.getRating()
        ));
  }

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
            movie.getRating()
        ))
        .collect(Collectors.toList());
  }

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
