package com.movie.app.dto;

import java.time.LocalDate;
import java.util.List;

public record MovieDetailDTO(String title,
                             LocalDate releaseDate,
                             String fullPosterUrl,
                             String overview,
                             String genres,
                             Double averageRating,
                             Integer runtime,
                             String language) {

}