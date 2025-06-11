package com.practice.BookingService.service.Impl;

import com.practice.BookingService.client.TrainClient;
import com.practice.BookingService.dto.Train;
import com.practice.BookingService.dto.TrainBookingEvent;
import com.practice.BookingService.exception.InsufficientSeatsException;
import com.practice.BookingService.exception.TrainNotFoundException;
import com.practice.BookingService.model.Booking;
import com.practice.BookingService.repository.BookingRepository;
import com.practice.BookingService.service.BookingServices;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingServices {

    private final BookingRepository bookingRepository;
    private final KafkaTemplate<String, TrainBookingEvent> kafkaTemplate;
    private final TrainClient trainClient;

    private final static Logger log = LoggerFactory.getLogger(BookingServiceImpl.class.getName());

    public Booking bookTicket(Booking booking) {

        log.info("Incoming request: {}", booking);

        Train train = trainClient.getTrainByNumber(booking.getTrainNumber());
        if (train == null) {
            throw new TrainNotFoundException(booking.getTrainNumber());
        }

        if (booking.getSeatCount() > train.getAvailableSeats()) {
            throw new InsufficientSeatsException(booking.getTrainNumber(), booking.getSeatCount(),
                    train.getAvailableSeats());
        }
        booking.setStatus("CONFIRMED");
        booking.setId(generateBookingId());
        log.info("booking information : {}",booking);
        Booking saved = bookingRepository.save(booking);

        TrainBookingEvent event = new TrainBookingEvent(
                "CONFIRMED",
                saved.getTrainNumber(),
                saved.getSeatCount()
        );

        try {
            kafkaTemplate.send("booking-events", event);
            log.info("Event triggered successfully: {}",event);

        } catch (Exception e) {
            log.error("Failed to send Kafka message", e);
            throw e;
        }
        return saved;
    }

    public String generateBookingId() {
        return "BOOK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public void cancelBooking(String id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStatus("CANCELLED");
        bookingRepository.save(booking);

        TrainBookingEvent event = new TrainBookingEvent(
                "CANCELLED",
                booking.getTrainNumber(),
                booking.getSeatCount()
        );

        try {
            kafkaTemplate.send("booking-events", event);
            log.info("Event triggered successfully: {}",event);
        } catch (Exception e) {
            log.error("Failed to send Kafka message", e);
            throw e;
        }
    }

    public List<Booking> getAllBookings() {
        return (List<Booking>) bookingRepository.findAll();
    }

    private void publishBookingEvent(String status, Booking booking) {
        TrainBookingEvent event = new TrainBookingEvent(status, booking.getTrainNumber(), booking.getSeatCount());
        kafkaTemplate.send("booking-events", event);
    }
}

