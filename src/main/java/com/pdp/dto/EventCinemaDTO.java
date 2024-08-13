package com.pdp.dto;

import java.time.LocalDateTime;

/**
 * @author Aliabbos Ashurov
 * @since 10/August/2024  10:48
 **/
public record EventCinemaDTO(
        int eventId,
        int movieId,
        int cinemaHallId,
        LocalDateTime showTime,
        double price,
        String capacity,
        String eventStatus,
        String eventDescription,
        String cinemaHallName,
        String cinemaHallCapacity,
        String cinemaHallStatus,
        String cinemaHallFacilities,
        int cinemaHallImageId,
        LocalDateTime cinemaHallCreatedAt,
        String imageGeneratedName,
        String imageExtension
) implements DTO {
}