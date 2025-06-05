package com.practice.BookingService.service.Impl;

import com.practice.BookingService.client.TrainClient;
import com.practice.BookingService.dto.Train;
import com.practice.BookingService.dto.TrainBookingEvent;
import com.practice.BookingService.exception.InsufficientSeatsException;
import com.practice.BookingService.exception.TrainNotFoundException;
import com.practice.BookingService.model.Booking;
import com.practice.BookingService.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements com.practice.BookingService.service.Impl.BookingServices {

    private final BookingRepository bookingRepository;
    private final KafkaTemplate<String, TrainBookingEvent> kafkaTemplate;
    private final TrainClient trainClient;

    public Booking bookTicket(Booking booking) {

        Train train = trainClient.getTrainByNumber(booking.getTrainNumber());
        if (train == null) {
            throw new TrainNotFoundException(booking.getTrainNumber());
        }

        if (booking.getSeatCount() > train.getAvailableSeats()) {
            throw new InsufficientSeatsException(booking.getTrainNumber(), booking.getSeatCount(),
                    train.getAvailableSeats());
        }
        booking.setStatus("CONFIRMED");
        Booking saved = bookingRepository.save(booking);

        TrainBookingEvent event = new TrainBookingEvent(
                "CONFIRMED",
                saved.getTrainNumber(),
                saved.getSeatCount()
        );

        kafkaTemplate.send("booking-events", event);
        return saved;
    }

    public void cancelBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStatus("CANCELLED");
        bookingRepository.save(booking);

        TrainBookingEvent event = new TrainBookingEvent(
                "CANCELLED",
                booking.getTrainNumber(),
                booking.getSeatCount()
        );

        kafkaTemplate.send("booking-events", event);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    private void publishBookingEvent(String status, Booking booking) {
        TrainBookingEvent event = new TrainBookingEvent(status, booking.getTrainNumber(), booking.getSeatCount());
        kafkaTemplate.send("booking-events", event);
    }
}

