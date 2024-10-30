package com.umpisa.online.exam.service.impl;

import com.umpisa.online.exam.dto.request.ReservationRequest;
import com.umpisa.online.exam.dto.request.UpdateReservationRequest;
import com.umpisa.online.exam.dto.response.ReservationResponse;
import com.umpisa.online.exam.entity.Reservation;
import com.umpisa.online.exam.mapstruct.ReservationMapper;
import com.umpisa.online.exam.repository.ReservationRepository;
import com.umpisa.online.exam.service.NotificationService;
import com.umpisa.online.exam.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ReservationMapper reservationMapper;

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public ReservationResponse  createReservation(ReservationRequest request) {
        String message = "";
        ReservationResponse response = new ReservationResponse();
        Reservation reservation = reservationMapper.map(request);
        reservation = reservationRepository.save(reservation);

        LocalDateTime date = reservation.getReservationDate();
        String resDate = date.toString().replace("T", " ");

        message = "Your reservation for " + resDate + " with the reservation ID " +
                reservation.getId() + " has been confirmed.";
        notificationService.sendNotification(message, reservation.getNotificationType());

        response.setMessage(message);
        return response;
    }

    @Override
    public ReservationResponse cancelReservation(Long id) {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        String message = "";
        ReservationResponse response = new ReservationResponse();

        if (reservation.isPresent()) {
            if (reservation.get().getStatus().equalsIgnoreCase("CANCELLED")) {
                message = "Your booking is already cancelled.";
            } else {
                reservation.get().setStatus("CANCELLED");
                reservationRepository.save(reservation.get());
                message = "Your Reservation has now been cancelled.";
                notificationService.sendNotification(message, reservation.get().getNotificationType());
            }
        } else {
            message = "Invalid Reservation ID";
        }

        response.setMessage(message);
        return response;
    }

    @Override
    public ReservationResponse updateReservation(Long id, UpdateReservationRequest request) {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        ReservationResponse response = new ReservationResponse();
        String message = "";

        if (reservation.isPresent()) {
            Reservation res = reservation.get();

            if ("CANCELLED".equalsIgnoreCase(res.getStatus())) {
                message = "Cannot update your reservations. Reservation already cancelled.";
            }

            res.setReservationDate(request.getReservationDate());
            res.setGuests(request.getGuests());
            reservationRepository.save(res);
            message = "Your reservation is now successfully updated.";
            notificationService.sendNotification(message, res.getNotificationType());
        } else {
            message =  "Invalid Reservation ID!";
        }

        response.setMessage(message);
        return response;
    }
}
