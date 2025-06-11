package com.practice.BookingService.model;

import com.google.cloud.spring.data.spanner.core.mapping.PrimaryKey;

import com.google.cloud.spring.data.spanner.core.mapping.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Table(name = "bookings")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Booking {
    @PrimaryKey
    private String id;

    private String trainNumber;

    private String passengerName;

    private LocalDate travelDate;

    private int seatCount;

    private String status;
}
