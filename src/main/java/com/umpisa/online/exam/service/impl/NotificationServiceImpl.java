package com.umpisa.online.exam.service.impl;

import com.umpisa.online.exam.entity.Reservation;
import com.umpisa.online.exam.repository.ReservationRepository;
import com.umpisa.online.exam.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private static final String EMAIL_NOTIFICATION = "EMAIL";
    private static final String SMS_NOTIFICATION = "SMS";

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public void sendNotification(String message, String notificationType) {
        if (SMS_NOTIFICATION.equalsIgnoreCase(notificationType)) {
            System.out.println("SMS Sent! : " + message);
        } else if (EMAIL_NOTIFICATION.equalsIgnoreCase(notificationType)){
            System.out.println("Email Sent! : " + message);
        } else {
            System.out.println("Invalid Notification Type!");
        }
    }

    @Override
    @Scheduled(fixedRate = 3600000)
    public void sendScheduledNotification() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusHours(4);

        List<Reservation> reservations = reservationRepository.findReservationsWithin4Hours(start, end);

        for (Reservation reservation : reservations) {
            sendNotification("Reminder! Your scheduled reservation is near ", reservation.getNotificationType());
        }
    }
}
