package com.movie.app.controller;

import com.movie.app.entity.Movie;
import com.movie.app.service.MovieService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MovieController {

  @Autowired
  private MovieService movieService;

  @GetMapping("/popular")
  public ResponseEntity<Page<Movie>> getPopularMovies(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "50") int size) {
    Pageable pageable = PageRequest.of(page, size, Sort.by("rating").descending());
    return ResponseEntity.ok(movieService.getPopularMovies(pageable));
  }

  @GetMapping("/search")
  public ResponseEntity<List<Movie>> searchMovies(@RequestParam String query) {
    return ResponseEntity.ok(movieService.searchMovies(query));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
    return ResponseEntity.ok(movieService.getMovieById(id));
  }

}
