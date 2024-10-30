package com.umpisa.online.exam.controller;

import com.umpisa.online.exam.dto.request.ReservationRequest;
import com.umpisa.online.exam.dto.request.UpdateReservationRequest;
import com.umpisa.online.exam.dto.response.ReservationResponse;
import com.umpisa.online.exam.entity.Reservation;
import com.umpisa.online.exam.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationRequest request) {
        return ResponseEntity.ok(reservationService.createReservation(request));
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<ReservationResponse> cancelReservation(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.cancelReservation(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ReservationResponse> updateReservation(@PathVariable Long id, @RequestBody UpdateReservationRequest request) {
        return ResponseEntity.ok(reservationService.updateReservation(id, request));
    }
}
