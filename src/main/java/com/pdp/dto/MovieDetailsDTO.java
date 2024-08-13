package com.pdp.dto;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Aliabbos Ashurov
 * @since 08/August/2024  11:35
 **/
public record MovieDetailsDTO(
        Integer movieId,
        String movieTitle,
        String movieDirector,
        Integer movieDuration,
        Double movieRating,
        String movieLanguage,
        String movieDescription,
        String movieThrillerLink,
        LocalDate movieReleaseDate,
        String movieImageGeneratedName,
        List<String> categories,
        List<String> sceneImages
) implements DTO {
}
