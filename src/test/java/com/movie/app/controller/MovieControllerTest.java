package com.movie.app.controller;

import com.movie.app.service.MovieService;
import com.movie.app.service.MovieValidationService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MovieControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Mock
  private MovieService movieService;

  @Mock
  private MovieValidationService movieValidationService;

  @InjectMocks
  private MovieController movieController;

  // Test for /movies/popular endpoint
  @Test
  public void testGetPopularMovies() throws Exception {
    mockMvc.perform(get("/movies/popular?api_key=test_key"))
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
    mockMvc.perform(get("/movies/popular?page=-1&api_key=test_key"))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testGetPopularMoviesWithInvalidSize() throws Exception {
    mockMvc.perform(get("/movies/popular?size=0&api_key=test_key"))
        .andExpect(status().isBadRequest());
  }

  // Test for /movies/search endpoint
  @Test
  public void testSearchMovies() throws Exception {
    mockMvc.perform(get("/movies/search?query=Inception&api_key=test_key"))
        .andExpect(status().isOk());
  }

  @Test
  public void testSearchMoviesWhenQueryIsEmpty() throws Exception {
    mockMvc.perform(get("/movies/search?query=&api_key=test_key"))
        .andExpect(status().isBadRequest());
  }

  // Test for /movies/{id} endpoint
  @Test
  public void testGetMovieById() throws Exception {
    mockMvc.perform(get("/movies/1?api_key=test_key"))
        .andExpect(status().isOk());
  }

  @Test
  public void testGetMovieByIdWhenIdIsInvalid() throws Exception {
    mockMvc.perform(get("/movies/0?api_key=test_key"))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testGetMovieByIdWhenIdDoesNotExist() throws Exception {
    mockMvc.perform(get("/movies/999999?api_key=test_key"))
        .andExpect(status().isNotFound());
  }
}