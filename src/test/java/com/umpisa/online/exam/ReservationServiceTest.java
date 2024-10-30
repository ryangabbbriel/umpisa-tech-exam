package com.umpisa.online.exam;

import com.umpisa.online.exam.dto.request.ReservationRequest;
import com.umpisa.online.exam.dto.request.UpdateReservationRequest;
import com.umpisa.online.exam.dto.response.ReservationResponse;
import com.umpisa.online.exam.entity.Reservation;
import com.umpisa.online.exam.mapstruct.ReservationMapper;
import com.umpisa.online.exam.repository.ReservationRepository;
import com.umpisa.online.exam.service.NotificationService;
import com.umpisa.online.exam.service.ReservationService;
import com.umpisa.online.exam.service.impl.ReservationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private NotificationService notificationService;

    @Mock
    private ReservationMapper reservationMapper;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createReservation_shouldCreateReservationAndSendNotification() {
        ReservationRequest request = createReservationRequest();
        LocalDateTime date = LocalDateTime.now().plusHours(4);

        Reservation res = new Reservation();
        res.setReservationDate(date);
        res.setName("Ryan Test");
        res.setEmail("ryantest@gmail.com");
        res.setGuests(2);
        res.setPhoneNumber("09214631921");
        res.setNotificationType("SMS");

        when(reservationMapper.map(request)).thenReturn(res);
        when(reservationRepository.save(any(Reservation.class))).thenReturn(res);

        ReservationResponse createdReservation = reservationService.createReservation(request);

        assertNotNull(createdReservation);
        verify(reservationRepository, times(1)).save(any(Reservation.class));
        verify(notificationService, times(1)).sendNotification(contains("confirmed"), eq("SMS"));
    }

    @Test
    public void updateReservation_shouldUpdateReservationAndSendNotification() {
        UpdateReservationRequest request = createUpdateReservationRequest();
        LocalDateTime date = LocalDateTime.now().plusHours(4);

        Reservation res = new Reservation();
        res.setReservationDate(date);
        res.setName("Ryan Test");
        res.setEmail("ryantest@gmail.com");
        res.setGuests(2);
        res.setPhoneNumber("09214631921");
        res.setNotificationType("SMS");

        when(reservationRepository.findById(any(Long.class))).thenReturn(Optional.of(res));

        ReservationResponse updatedReservation =
                reservationService.updateReservation(any(Long.class), request);

        assertNotNull(updatedReservation);
        verify(reservationRepository, times(1)).findById(any(Long.class));
        verify(reservationRepository, times(1)).save(any(Reservation.class));
        verify(notificationService, times(1)).sendNotification(contains("updated"), eq("SMS"));

    }

    @Test
    public void cancelReservation_shouldCancelReservationAndSendNotification() {
        LocalDateTime date = LocalDateTime.now().plusHours(4);

        Reservation res = new Reservation();
        res.setReservationDate(date);
        res.setName("Ryan Test");
        res.setEmail("ryantest@gmail.com");
        res.setGuests(2);
        res.setPhoneNumber("09214631921");
        res.setNotificationType("SMS");
        res.setStatus("ACTIVE");

        when(reservationRepository.findById(any(Long.class))).thenReturn(Optional.of(res));

        ReservationResponse response = reservationService.cancelReservation(any(Long.class));

        assertNotNull(response);
        assertEquals(res.getStatus(), "CANCELLED");
        verify(reservationRepository, times(1)).findById(any(Long.class));
        verify(reservationRepository, times(1)).save(any(Reservation.class));
        verify(notificationService, times(1)).sendNotification(contains("cancelled"), eq("SMS"));
    }


    public ReservationRequest createReservationRequest() {
        LocalDateTime date = LocalDateTime.now().plusHours(4);

        ReservationRequest reservation = new ReservationRequest();
        reservation.setReservationDate(date);
        reservation.setName("Ryan Test");
        reservation.setEmail("ryantest@gmail.com");
        reservation.setGuests(2);
        reservation.setPhoneNumber("09214631921");
        reservation.setNotificationType("SMS");

        return reservation;
    }

    public UpdateReservationRequest createUpdateReservationRequest() {
        LocalDateTime date = LocalDateTime.now().plusDays(1);

        UpdateReservationRequest request = new UpdateReservationRequest();
        request.setGuests(4);
        request.setReservationDate(date);

        return request;
    }
}
