package com.movie.app.service;

import com.movie.app.dto.MovieDetailDTO;
import com.movie.app.dto.MovieSummaryDTO;
import com.movie.app.entity.Movie;
import com.movie.app.exception.MovieNotFoundException;
import com.movie.app.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieServiceTest {

  @Mock
  private MovieRepository movieRepository;

  @InjectMocks
  private MovieService movieService;

  private Movie movie1;
  private Movie movie2;
  private Pageable pageable;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    // Initialize the Movie objects with appropriate values
    movie1 = new Movie();
    movie1.setId(1L);
    movie1.setTitle("Movie 1");
    movie1.setReleaseDate(LocalDate.of(2022, 1, 1));
    movie1.setPosterUrl("url1");
    movie1.setOverview("Overview 1");
    movie1.setGenres("Action, Drama");
    movie1.setRating(8.5);
    movie1.setRuntime(120);
    movie1.setLanguage("English");

    movie2 = new Movie();
    movie2.setId(2L);
    movie2.setTitle("Movie 2");
    movie2.setReleaseDate(LocalDate.of(2021, 5, 5));
    movie2.setPosterUrl("url2");
    movie2.setOverview("Overview 2");
    movie2.setGenres("Comedy");
    movie2.setRating(7.0);
    movie2.setRuntime(90);
    movie2.setLanguage("Spanish");

    pageable = mock(Pageable.class);
  }

  @Test
  void testGetPopularMovies() {
    List<Movie> movies = Arrays.asList(movie1, movie2);
    Page<Movie> moviePage = new PageImpl<>(movies, pageable, movies.size());

    when(movieRepository.findAll(pageable)).thenReturn(moviePage);

    Page<MovieSummaryDTO> movieSummaryDTOs = movieService.getPopularMovies(pageable);

    assertNotNull(movieSummaryDTOs);
    assertEquals(2, movieSummaryDTOs.getContent().size());
    assertEquals(movie1.getTitle(), movieSummaryDTOs.getContent().get(0).title());
    assertEquals(movie2.getTitle(), movieSummaryDTOs.getContent().get(1).title());
  }

  @Test
  void testSearchMovies_found() {
    List<Movie> movies = Arrays.asList(movie1, movie2);
    when(movieRepository.findByTitleContainingIgnoreCase("Movie")).thenReturn(movies);

    List<MovieSummaryDTO> movieSummaryDTOs = movieService.searchMovies("Movie");

    assertNotNull(movieSummaryDTOs);
    assertEquals(2, movieSummaryDTOs.size());
    assertEquals(movie1.getTitle(), movieSummaryDTOs.get(0).title());
    assertEquals(movie2.getTitle(), movieSummaryDTOs.get(1).title());
  }

  @Test
  void testSearchMovies_notFound() {
    when(movieRepository.findByTitleContainingIgnoreCase("NonExistent")).thenReturn(List.of());

    MovieNotFoundException exception = assertThrows(MovieNotFoundException.class, () -> movieService.searchMovies("NonExistent"));

    assertEquals("No movies found matching query: NonExistent", exception.getMessage());
  }

  @Test
  void testGetMovieById_found() {
    when(movieRepository.findById(1L)).thenReturn(Optional.of(movie1));

    MovieDetailDTO movieDetailDTO = movieService.getMovieById(1L);

    assertNotNull(movieDetailDTO);
    assertEquals(movie1.getTitle(), movieDetailDTO.title());
    assertEquals(movie1.getGenres(), movieDetailDTO.genres());
    assertEquals(movie1.getRating(), movieDetailDTO.averageRating());
    assertEquals(movie1.getOverview(), movieDetailDTO.overview());
    assertEquals(movie1.getLanguage(), movieDetailDTO.language());
  }

  @Test
  void testGetMovieById_notFound() {
    when(movieRepository.findById(3L)).thenReturn(Optional.empty());

    MovieNotFoundException exception = assertThrows(MovieNotFoundException.class, () -> movieService.getMovieById(3L));

    assertEquals("Movie with ID 3 not found.", exception.getMessage());
  }
}
