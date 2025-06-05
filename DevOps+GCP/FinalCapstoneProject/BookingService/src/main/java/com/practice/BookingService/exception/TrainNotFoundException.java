package com.practice.BookingService.exception;

public class TrainNotFoundException extends RuntimeException {
    public TrainNotFoundException(String trainNumber) {
        super("Train not found with number: " + trainNumber);
    }
}
