package com.umpisa.online.exam.repository;

import com.umpisa.online.exam.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT res FROM Reservation res WHERE res.reservationDate BETWEEN :start AND :end AND res.status = 'ACTIVE'")
    List<Reservation> findReservationsWithin4Hours(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
