package com.practice.NotificaitonService.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TrainBookingEvent {
    private String status;
    private String trainNumber;
    private int seats;
}
