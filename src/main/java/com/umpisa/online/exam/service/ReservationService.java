package com.umpisa.online.exam.service;

import com.umpisa.online.exam.dto.request.ReservationRequest;
import com.umpisa.online.exam.dto.request.UpdateReservationRequest;
import com.umpisa.online.exam.dto.response.ReservationResponse;
import com.umpisa.online.exam.entity.Reservation;

import java.util.List;

public interface ReservationService {

    List<Reservation> getAllReservations();
    ReservationResponse createReservation(ReservationRequest request);
    ReservationResponse cancelReservation(Long id);
    ReservationResponse updateReservation(Long id, UpdateReservationRequest request);
}
