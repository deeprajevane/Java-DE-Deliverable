package com.practice.NotificaitonService.consumer;


import com.practice.NotificaitonService.dto.TrainBookingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class BookingNotificationConsumer {

    private final static Logger log = LoggerFactory.getLogger(BookingNotificationConsumer.class.getName());

    @KafkaListener(
            topics = "booking-events",
            groupId = "ticket-notification-group",
            containerFactory = "employeeKafkaListenerContainerFactory"
    )
    public void consume(TrainBookingEvent event) {
        try{
            log.info("Received Ticket Event: {}", event);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String message = String.format("Notification: Booking %s for Train %s (%d seats)",
                event.getStatus(),
                event.getTrainNumber(),
                event.getSeats()
        );

        //Here I have to setup email
        log.info(message);
    }
}

