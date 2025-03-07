package com.movie.app.repository;

import com.movie.app.entity.Movie;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
  Page<Movie> findAll(Pageable pageable);
  List<Movie> findByTitleContainingIgnoreCase(String title);
}