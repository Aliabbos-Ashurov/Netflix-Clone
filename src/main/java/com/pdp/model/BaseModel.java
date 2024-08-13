package com.pdp.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * @author Aliabbos Ashurov
 * @since 04/August/2024  16:31
 **/
@Getter
@Setter
@ToString(of = "id")
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public abstract class BaseModel {
    @Builder.Default
    private Integer id = -1;
    @Builder.Default
    private LocalDateTime createTime = LocalDateTime.now();
    @Builder.Default
    private boolean isDeleted = false;

    public BaseModel() {
        this.id = new Random().nextInt(Integer.MAX_VALUE);
        this.createTime = LocalDateTime.now();
        this.isDeleted = false;
    }
}
