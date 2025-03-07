package com.movie.app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity class representing a movie in the system.
 * This class is mapped to the "movie" table in the database.
 * It contains the movie's details such as title, release date, genres, rating, etc.
 *
 */
@Entity
@Table(name = "movie")
@Getter
@Setter
public class Movie {

  /**
   * The unique identifier of the movie.
   * This is the primary key of the "movie" table and is generated automatically.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  /**
   * The title of the movie.
   */
  private String title;

  /**
   * The release date of the movie.
   */
  private LocalDate releaseDate;

  /**
   * The URL of the movie's poster image.
   */
  private String posterUrl;

  /**
   * A brief overview or description of the movie's plot.
   */
  private String overview;

  /**
   * A comma-separated list of genres associated with the movie.
   */
  private String genres;

  /**
   * The average rating of the movie, represented as a Double.
   */
  private Double rating;

  /**
   * The runtime of the movie in minutes.
   */
  private Integer runtime;

  /**
   * The language in which the movie is presented.
   */
  private String language;
}
