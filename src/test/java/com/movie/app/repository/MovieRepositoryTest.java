package com.movie.app.repository;

import com.movie.app.entity.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MovieRepositoryTest {

  @Mock
  private MovieRepository movieRepository;

  private Movie movie1;
  private Movie movie2;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);

    // Setup sample movie entities
    movie1 = new Movie();
    movie1.setId(1L);
    movie1.setTitle("Inception");

    movie2 = new Movie();
    movie2.setId(2L);
    movie2.setTitle("Interstellar");
  }

  @Test
  public void testFindAll() {
    Pageable pageable = PageRequest.of(0, 10);
    List<Movie> movies = Arrays.asList(movie1, movie2);
    Page<Movie> moviePage = new PageImpl<>(movies, pageable, movies.size());

    when(movieRepository.findAll(pageable)).thenReturn(moviePage);

    Page<Movie> result = movieRepository.findAll(pageable);

    assertNotNull(result);
    assertEquals(2, result.getContent().size());
    verify(movieRepository, times(1)).findAll(pageable);
  }

  @Test
  public void testFindByTitleContainingIgnoreCase() {
    List<Movie> movies = Arrays.asList(movie1, movie2);

    when(movieRepository.findByTitleContainingIgnoreCase("inception")).thenReturn(movies);

    List<Movie> result = movieRepository.findByTitleContainingIgnoreCase("inception");

    assertNotNull(result);
    assertEquals(2, result.size());
    verify(movieRepository, times(1)).findByTitleContainingIgnoreCase("inception");
  }
}
