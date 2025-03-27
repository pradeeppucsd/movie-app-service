package com.movie.app.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.movie.app.dto.MovieDetailDTO;
import com.movie.app.dto.MovieSummaryDTO;
import com.movie.app.entity.Movie;
import com.movie.app.repository.MovieRepository;
import com.movie.app.service.MovieService;
import com.movie.app.service.MovieValidationService;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class MovieControllerTest {

  public static final String TEST_API_KEY = "test_key";
  @Autowired
  private MockMvc mockMvc;

  @Mock
  private MovieService movieService;

  @Mock
  private MovieValidationService movieValidationService;

  @InjectMocks
  private MovieController movieController;
  @Mock
  private MovieRepository movieRepository;

  // Test for /movies/popular endpoint
  @Test
  public void testGetPopularMovies() throws Exception {
    mockMvc.perform(get("/movies/popular?api_key="+TEST_API_KEY))
        .andExpect(status().isOk());
  }

  @Test
  public void testGetPopularMoviesWhenApiKeyIsInvalid() throws Exception {
    mockMvc.perform(get("/movies/popular?api_key=xzq"))
        .andExpect(status().isUnauthorized());
  }

  @Test
  public void testGetPopularMoviesWhenApiKeyIsMissing() throws Exception {
    mockMvc.perform(get("/movies/popular"))
        .andExpect(status().isUnauthorized());
  }

  @Test
  public void testGetPopularMoviesWithInvalidPage() throws Exception {
    mockMvc.perform(get("/movies/popular?page=-1&api_key="+TEST_API_KEY))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testGetPopularMoviesWithInvalidSize() throws Exception {
    mockMvc.perform(get("/movies/popular?size=0&api_key="+TEST_API_KEY))
        .andExpect(status().isBadRequest());
  }

  // Test for /movies/search endpoint
  @Test
  public void testSearchMovies() throws Exception {
    mockMvc.perform(get("/movies/search?query=Inception&api_key="+TEST_API_KEY))
        .andExpect(status().isOk());
  }

  @Test
  public void testSearchMoviesWhenQueryIsEmpty() throws Exception {
    mockMvc.perform(get("/movies/search?query=&api_key="+TEST_API_KEY))
        .andExpect(status().isBadRequest());
  }

  // Test for /movies/{id} endpoint
  @Test
  public void testGetMovieById() throws Exception {
    mockMvc.perform(get("/movies/1?api_key="+TEST_API_KEY))
        .andExpect(status().isOk());
  }

  @Test
  public void testGetMovieByIdWhenIdIsInvalid() throws Exception {
    mockMvc.perform(get("/movies/0?api_key="+TEST_API_KEY))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testGetMovieByIdWhenIdDoesNotExist() throws Exception {
    mockMvc.perform(get("/movies/999999?api_key="+TEST_API_KEY))
        .andExpect(status().isNotFound());
  }

  @Test
  public void testGetMovieByIdContentValidation() throws Exception {
    MovieDetailDTO movieDetailDTO = new MovieDetailDTO("Inception",
        LocalDate.of(2010, 7, 16), "interstellar.jpg", "A mind-bending thriller", "Sci-Fi, Drama", 8.8, 169, "English");

    // Mock the searchMovies method
    when(movieService.getMovieById(1L)).thenReturn(movieDetailDTO);

    // Perform the GET request for /movies/search?query=Inception&api_key=
    mockMvc.perform(get("/movies/1?" + "&api_key=" + TEST_API_KEY))
        .andExpect(status().isOk())  // Expect HTTP 200 OK
        .andExpect(jsonPath("$.title").value("Inception"))
        .andExpect(jsonPath("$.overview").value("A mind-bending thriller"))
        .andExpect(jsonPath("$.averageRating").value(8.8)
        );
  }

  @Test
  public void testSearchMoviesContentValidation() throws Exception {
    String query = "Inception";
    final MovieSummaryDTO movieDetailDTO = new MovieSummaryDTO(5L, "Inception1", LocalDate.of(2010, 7, 16), "inception.jpg", 8.8,7);
    final Movie movie = new Movie();
    movie.setTitle("Inception");
    movie.setOverview("A mind-bending thriller");
    movie.setRating(8.8);
    movie.setReleaseDate(LocalDate.of(2010, 7, 16));
    movie.setPopularity(7);
    movie.setPosterUrl("inception.jpg");

    // Mock the searchMovies method
    when(movieRepository.findByTitleContainingIgnoreCase(query)).thenReturn(List.of(movie));
    //when(movieService.searchMovies(query)).thenReturn(List.of(movieDetailDTO));

    // Perform the GET request for /movies/search?query=Inception&api_key=
    mockMvc.perform(get("/movies/search?query=" + query + "&api_key=" + TEST_API_KEY))
        .andExpect(status().isOk())  // Expect HTTP 200 OK
        .andExpect(jsonPath("$[0].title").value("Inception"))
        .andExpect(jsonPath("$[0].posterUrl").value("inception.jpg"))
        .andExpect(jsonPath("$[0].averageRating").value(8.8));
  }
}