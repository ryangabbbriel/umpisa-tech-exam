package com.umpisa.online.exam.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UpdateReservationRequest {

    private LocalDateTime reservationDate;
    private int guests;
}
