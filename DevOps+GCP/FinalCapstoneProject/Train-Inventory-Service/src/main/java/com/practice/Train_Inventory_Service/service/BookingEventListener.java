package com.practice.Train_Inventory_Service.service;

import com.practice.Train_Inventory_Service.dto.TrainBookingEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingEventListener {

    private final TrainInventoryService inventoryService;
    private final static Logger log = LoggerFactory.getLogger(BookingEventListener.class.getName());

    @KafkaListener(topics = "booking-events", groupId = "train-inventory-group",containerFactory = "employeeKafkaListenerContainerFactory")
    public void handleBookingEvent(TrainBookingEvent event) {

        try{
            log.info("Received Ticket Event: {}", event);

            String action = event.getStatus();
            String trainNumber = event.getTrainNumber();
            int seats = event.getSeats();
            if (trainNumber == null || seats <= 0|| action == null) {
                log.error("Invalid booking event data: {}", event);
                return;
            }
            switch (action.toUpperCase()) {
                case "CONFIRMED":
                    inventoryService.reduceSeats(trainNumber, seats);
                    log.info("Reduced {} seats for train {}", seats, trainNumber);
                    break;
                case "CANCELLED":
                    inventoryService.increaseSeats(trainNumber, seats);
                    log.info("Increased {} seats for train {} due to cancellation", seats, trainNumber);
                    break;
                case "UPDATED":
                    inventoryService.updatedSeats(trainNumber, seats);
                    log.info("Adjusted seats for train {} due to update", trainNumber);
                    break;
                default:
                    log.warn("Unhandled booking status: {}", action);
            }
        } catch (Exception e) {
            log.error("Error processing booking event: {}", event, e);
            throw new RuntimeException(e);
        }

    }
}
