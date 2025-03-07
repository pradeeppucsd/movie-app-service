package com.movie.app.dto;

import java.util.List;

/**
 * A generic pagination response DTO that encapsulates paginated data.
 *
 * @param <T>         The type of content in the response.
 * @param content     The list of items on the current page.
 * @param currentPage The current page number
 * @param totalPages  The total number of pages available.
 * @param totalItems  The total number of items across all pages.
 */
public record PaginatedResponse<T>(
    List<T> content,
    int currentPage,
    int totalPages,
    long totalItems) {

}