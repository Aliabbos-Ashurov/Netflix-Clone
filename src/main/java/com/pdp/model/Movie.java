package com.pdp.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

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
public class Movie extends BaseModel {
    private String title;
    private String director;
    private Integer duration;
    private double rating;
    private String language;
    private String description;
    private String thrillerLink;
    private Integer imageID;
    private LocalDate releaseDate;
    private boolean isDeleted;
}
