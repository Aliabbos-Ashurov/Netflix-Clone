package com.pdp.dto;

/**
 * @author Aliabbos Ashurov
 * @since 10/August/2024  09:16
 **/
public record EventDTO(
        Integer cinemaHallId,
        Integer month,
        Integer day,
        Integer year,
        Integer hour,
        Double price,
        String description,
        Integer movieId
) implements DTO {
}
