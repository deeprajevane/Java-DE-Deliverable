package com.practice.BookingService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainBookingEvent {
    private String status;      // BOOKED or CANCELLED
    private String trainNumber;
    private int seats;
}

