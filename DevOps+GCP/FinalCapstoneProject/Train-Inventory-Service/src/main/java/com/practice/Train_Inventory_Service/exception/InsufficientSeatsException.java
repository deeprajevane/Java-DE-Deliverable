package com.practice.Train_Inventory_Service.exception;

public class InsufficientSeatsException extends RuntimeException {
    public InsufficientSeatsException(String trainNumber, int requested, int available) {
        super("Insufficient seats on train " + trainNumber + ": requested " + requested + ", available " + available);
    }
}
