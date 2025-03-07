package com.movie.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MovieController {

  @GetMapping("/popular")
  public ResponseEntity<String> getPopularMovies() {
    return ResponseEntity.ok("Popular Movies");
  }
}
