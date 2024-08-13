package com.pdp.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * @author Aliabbos Ashurov
 * @since 04/August/2024  16:29
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class Event extends BaseModel {
    private Integer movieID;
    private Integer cinemaHallID;
    private LocalDateTime showTime;
    private double price;
    private String status;
    private String description;
    private String capacity;
}
