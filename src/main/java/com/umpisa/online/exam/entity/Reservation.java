package com.umpisa.online.exam.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Reservations")
@Setter
@Getter
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String phoneNumber;
    private String email;
    private LocalDateTime reservationDate;
    private int guests;
    private String notificationType;
    private String status;

    @PrePersist
    void preInsert() {
        if (status == null) {
            status = "ACTIVE";
        }
    }
}
