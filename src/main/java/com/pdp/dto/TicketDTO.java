package com.pdp.dto;

import java.time.LocalDateTime;

/**
 * @author Aliabbos Ashurov
 * @since 10/August/2024  18:45
 **/
public record TicketDTO(
        LocalDateTime showTime,
        double price,
        String status,
        int rowSeat,
        int columnSeat,
        int userID,
        String movieTitle,
        String imageName,
        String imageExtension
) {
}