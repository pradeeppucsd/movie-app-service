
package com.movie.app.controller;

import org.junit.jupiter.api.Test;
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

  @Test
  public void testGetPopularMovies() throws Exception {
    mockMvc.perform(get("/movies/popular?api_key=test_key"))
        .andExpect(status().isOk());
  }

  @Test
  public void testGetPopularMoviesWhenisUnauthorized() throws Exception {
    mockMvc.perform(get("/movies/popular?api_key=xzq"))
        .andExpect(status().isUnauthorized());
  }

  @Test
  public void testGetPopularMoviesWhenApiKeyIsMissing() throws Exception {
    mockMvc.perform(get("/movies/popular"))
        .andExpect(status().isUnauthorized());
  }
}