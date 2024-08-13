package com.pdp.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * @author Aliabbos Ashurov
 * @since 05/August/2024  11:31
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class RolePermissions extends BaseModel {
    private Integer roleID;
    private Integer permissionID;
}
