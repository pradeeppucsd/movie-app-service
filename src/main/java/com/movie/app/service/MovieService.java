package com.movie.app.service;

import com.movie.app.entity.Movie;
import com.movie.app.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class MovieService {

  @Autowired
  private MovieRepository movieRepository;

  public Movie getMovieById(Long id) {
    return movieRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }
}