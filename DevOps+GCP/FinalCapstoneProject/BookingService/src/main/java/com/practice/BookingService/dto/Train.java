package com.practice.BookingService.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class Train {
    private String trainNumber;
    private String trainName;
    private String source;
    private String destination;
    private int availableSeats;
    private int totalSeats;
    private LocalDate travelDate;
}

