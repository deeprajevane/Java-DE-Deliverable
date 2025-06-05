package com.practice.Train_Inventory_Service.exception;

public class TrainNotFoundException extends RuntimeException {
    public TrainNotFoundException(String trainNumber) {
        super("Train with number " + trainNumber + " not found");
    }
}


