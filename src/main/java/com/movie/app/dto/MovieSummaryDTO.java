package com.movie.app.dto;

import java.time.LocalDate;

public record MovieSummaryDTO(Long movieId,
                              String title,
                              LocalDate releaseDate,
                              String posterUrl,
                              Double averageRating) {
}
