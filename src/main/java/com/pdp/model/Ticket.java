package com.pdp.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

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
public class Ticket extends BaseModel {
    private Integer userID;
    private Integer movieID;
    private Integer cinema_hall_id;
    private String status;
    private LocalDateTime showTime;
    private Integer rowSeat;
    private Integer columnSeat;
    private double price;
    private String paymentMethod;
    private LocalDateTime purchaseTime;
}
