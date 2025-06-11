package com.practice.NotificaitonService.consumer;


import com.practice.NotificaitonService.dto.TrainBookingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class BookingNotificationConsumer {

    @Autowired
    JavaMailSender mailSender;

    @Value("${train.confirmation.email}")
    private String tempEmail;

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

        //Here I have to set up email
        log.info(message);
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(tempEmail);
        mailMessage.setSubject("Ticket Status for Train Number - #"+ event.getTrainNumber() );
        mailMessage.setText("Hello,\n\nYour "+ event.getSeats()+ " ticket for Train Number " + event.getTrainNumber() +
                " is " + event.getStatus() + ".\n\nThank you!");
        try {
            mailSender.send(mailMessage);
            log.info("Ticket confirmation email sent successfully to:  " );
        } catch (MailException e) {

            log.info("Failed to send ticket confirmation email : {} " , e.getMessage());

        }
    }
}

