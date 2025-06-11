package com.practice.BookingService.service;



import com.practice.BookingService.client.TrainClient;
import com.practice.BookingService.dto.Train;
import com.practice.BookingService.dto.TrainBookingEvent;
import com.practice.BookingService.exception.InsufficientSeatsException;
import com.practice.BookingService.exception.TrainNotFoundException;
import com.practice.BookingService.model.Booking;
import com.practice.BookingService.repository.BookingRepository;
import com.practice.BookingService.service.Impl.BookingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingServiceImplTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private KafkaTemplate<String, TrainBookingEvent> kafkaTemplate;

    @Mock
    private TrainClient trainClient;

    @InjectMocks
    private BookingServiceImpl bookingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Booking getSampleBooking() {
        return Booking.builder()
                .id("Book-1")
                .trainNumber("12345")
                .seatCount(2)
                .status(null)
                .build();
    }

    private Train getSampleTrain() {
        return Train.builder()
                .trainNumber("12345")
                .trainName("Express")
                .availableSeats(10)
                .build();
    }

    @Test
    void bookTicket_SuccessfulBooking() {
        Booking booking = getSampleBooking();
        Train train = getSampleTrain();

        when(trainClient.getTrainByNumber("12345")).thenReturn(train);
        when(bookingRepository.save(any(Booking.class))).thenAnswer(i -> i.getArgument(0));

        Booking result = bookingService.bookTicket(booking);

        assertEquals("CONFIRMED", result.getStatus());
        verify(kafkaTemplate, times(1)).send(eq("booking-events"), any(TrainBookingEvent.class));
    }

    @Test
    void bookTicket_TrainNotFound_ThrowsException() {
        Booking booking = getSampleBooking();
        when(trainClient.getTrainByNumber("12345")).thenReturn(null);

        assertThrows(TrainNotFoundException.class, () -> bookingService.bookTicket(booking));
        verify(bookingRepository, never()).save(any());
    }

    @Test
    void bookTicket_InsufficientSeats_ThrowsException() {
        Booking booking = getSampleBooking();
        booking.setSeatCount(20); // requesting more than available

        Train train = getSampleTrain();
        train.setAvailableSeats(5);

        when(trainClient.getTrainByNumber("12345")).thenReturn(train);

        assertThrows(InsufficientSeatsException.class, () -> bookingService.bookTicket(booking));
        verify(bookingRepository, never()).save(any());
    }

    @Test
    void cancelBooking_ValidId_UpdatesStatus() {
        Booking booking = getSampleBooking();
        booking.setStatus("CONFIRMED");

        when(bookingRepository.findById("Book-1")).thenReturn(Optional.of(booking));
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        bookingService.cancelBooking("Book-1");

        assertEquals("CANCELLED", booking.getStatus());
        verify(kafkaTemplate, times(1)).send(eq("booking-events"), any(TrainBookingEvent.class));
    }

    @Test
    void getAllBookings_ReturnsList() {
        when(bookingRepository.findAll()).thenReturn(List.of(getSampleBooking()));
        List<Booking> bookings = bookingService.getAllBookings();
        assertEquals(1, bookings.size());
    }
}

