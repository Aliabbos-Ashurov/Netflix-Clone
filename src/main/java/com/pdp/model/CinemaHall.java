package com.pdp.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalTime;

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
public class CinemaHall extends BaseModel {
    private String name;
    private String capacity;
    private String status;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private String facilities;
    private Integer imageID;
}
