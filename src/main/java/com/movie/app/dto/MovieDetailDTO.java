package com.movie.app.dto;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) representing detailed information about a movie.
 * This class is used to transfer the movie details between layers of the application.
 *
 * @param title The title of the movie.
 * @param releaseDate The release date of the movie.
 * @param fullPosterUrl The full URL of the movie's poster image.
 * @param overview A detailed overview or description of the movie's plot.
 * @param genres The genres associated with the movie.
 * @param averageRating The average rating of the movie.
 * @param runtime The runtime of the movie in minutes.
 * @param language The language of the movie.
 */
public record MovieDetailDTO(
    String title,
    LocalDate releaseDate,
    String fullPosterUrl,
    String overview,
    String genres,
    Double averageRating,
    Integer runtime,
    String language) {
}
