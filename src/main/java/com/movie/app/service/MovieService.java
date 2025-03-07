package com.movie.app.service;

import com.movie.app.dto.MovieDetailDTO;
import com.movie.app.dto.MovieSummaryDTO;
import com.movie.app.entity.Movie;
import com.movie.app.repository.MovieRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class MovieService {

  @Autowired
  private MovieRepository movieRepository;

  public Page<MovieSummaryDTO> getPopularMovies(Pageable pageable) {
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
    return movieRepository.findByTitleContainingIgnoreCase(query)
        .stream()
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
    Movie movie = movieRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

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