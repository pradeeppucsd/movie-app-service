package com.movie.app.dto;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) representing a summary of a movie.
 * This class is used to transfer basic movie information, typically for listing purposes.
 *
 * @param movieId The unique identifier of the movie.
 * @param title The title of the movie.
 * @param releaseDate The release date of the movie.
 * @param posterUrl The URL of the movie's poster image.
 * @param averageRating The average rating of the movie.
 */
public record MovieSummaryDTO(
    Long movieId,
    String title,
    LocalDate releaseDate,
    String posterUrl,
    Double averageRating) {
}
