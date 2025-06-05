package com.practice.BookingService.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "bookings")
@Data
@Builder
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String trainNumber;
    @Column(nullable = false)
    private String passengerName;
    @Column(nullable = false)
    private LocalDate travelDate;
    @Column(nullable = false)
    private int seatCount;
    @Column(nullable = false)
    private String status;
}
