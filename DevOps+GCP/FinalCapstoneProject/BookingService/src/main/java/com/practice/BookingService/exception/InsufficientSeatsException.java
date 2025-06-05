package com.practice.BookingService.exception;

public class InsufficientSeatsException extends RuntimeException {
    public InsufficientSeatsException(String trainNumber, int requested, int available) {
        super("Train " + trainNumber + " has only " + available + " seats. Requested: " + requested);
    }
}
