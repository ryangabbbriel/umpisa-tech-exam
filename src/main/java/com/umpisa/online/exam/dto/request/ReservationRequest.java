package com.umpisa.online.exam.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationRequest {

    private String name;
    private String phoneNumber;
    private String email;
    private LocalDateTime reservationDate;
    private int guests;
    private String notificationType;
}
