package com.movie.app.service;

import com.movie.app.entity.Movie;
import com.movie.app.repository.MovieRepository;
import java.util.List;
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

  public Page<Movie> getPopularMovies(Pageable pageable) {
    return movieRepository.findAll(pageable);
  }

  public List<Movie> searchMovies(String query) {
    return movieRepository.findByTitleContainingIgnoreCase(query);
  }

  public Movie getMovieById(Long id) {
    return movieRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }
}