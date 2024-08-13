package com.pdp.dto;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Aliabbos Ashurov
 * @since 07/August/2024  10:39
 **/
public record MovieCreatingDTO(
        String title,
        String director,
        List<Integer> categories,
        String description,
        int duration,
        String language,
        MultipartFile movieImage,
        List<MultipartFile> sceneImages,
        double rating,
        String thrillerLink,
        LocalDate releaseDate
) {
}