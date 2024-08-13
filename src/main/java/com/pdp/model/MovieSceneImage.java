package com.pdp.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

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
public class MovieSceneImage extends BaseModel {
    private Integer movieID;
    private Integer imageID;
}
