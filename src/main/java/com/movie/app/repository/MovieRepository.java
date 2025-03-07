package com.movie.app.repository;

import com.movie.app.entity.Movie;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link Movie} entities.
 * Extends {@link JpaRepository} to provide standard CRUD operations.
 */
public interface MovieRepository extends JpaRepository<Movie, Long> {

  /**
   * Retrieves a paginated list of all movies.
   *
   * @param pageable the pagination and sorting information
   * @return a {@link Page} containing the movies
   */
  Page<Movie> findAll(Pageable pageable);

  /**
   * Searches for movies with a title that contains the specified keyword,
   * ignoring case sensitivity.
   *
   * @param title the keyword to search for in movie titles
   * @return a list of movies whose titles contain the given keyword
   */
  List<Movie> findByTitleContainingIgnoreCase(String title);
}
