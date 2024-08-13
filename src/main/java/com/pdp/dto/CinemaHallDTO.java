package com.pdp.dto;

import java.time.LocalTime;

/**
 * @author Aliabbos Ashurov
 * @since 05/August/2024  13:21
 **/
public record CinemaHallDTO(String name, String capacity, String status, LocalTime openingHours,
                            LocalTime closingHours)
        implements DTO {
}
