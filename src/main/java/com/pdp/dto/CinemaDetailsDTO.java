package com.pdp.dto;

import java.sql.Time;
import java.sql.Timestamp;

/**
 * @author Aliabbos Ashurov
 * @since 10/August/2024  09:30
 **/
public record CinemaDetailsDTO(
        int cinemaHallId,
        String cinemaHallName,
        String capacity,
        String cinemaHallStatus,
        Time openingHours,
        Time closingHours,
        String facilities,
        Timestamp cinemaCreatedAt,
        Integer imageId,
        String imageGeneratedName,
        String imageExtension,
        Timestamp imageCreatedAt
) implements DTO {
}
