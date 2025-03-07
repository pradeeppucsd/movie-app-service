package com.movie.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the MovieAppService Spring Boot application.
 * This class contains the main method that starts the application.
 * It is annotated with {@link SpringBootApplication} to enable
 * auto-configuration, component scanning, and property support for the application.
 */
@SpringBootApplication
public class MovieAppServiceApplication {

  /**
   * The main method that launches the MovieAppService application.
   * It initializes the Spring Boot application context and starts the embedded server.
   *
   * @param args the command-line arguments passed to the application (not used here)
   */
  public static void main(String[] args) {
    SpringApplication.run(MovieAppServiceApplication.class, args);
  }
}
