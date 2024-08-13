package com.pdp.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * @author Aliabbos Ashurov
 * @since 04/August/2024  16:30
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class Users extends BaseModel {
    private String fullname;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private Integer imageID;
    private boolean isActive;
}
