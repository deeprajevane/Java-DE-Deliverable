package com.practice.BookingService.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.BookingService.model.Booking;
import com.practice.BookingService.service.Impl.BookingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingServiceImpl bookingService;

    @Autowired
    private ObjectMapper objectMapper;

    private Booking booking;

    @BeforeEach
    void setUp() {
        booking = Booking.builder()
                .id("Book-1")
                .trainNumber("12345")
                .passengerName("John Doe")
                .seatCount(2)
                .status("CONFIRMED")
                .build();
    }

    @Test
    void testBookTicket() throws Exception {
        when(bookingService.bookTicket(any(Booking.class))).thenReturn(booking);

        mockMvc.perform(post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(booking)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.trainNumber").value("12345"))
                .andExpect(jsonPath("$.passengerName").value("John Doe"))
                .andExpect(jsonPath("$.status").value("CONFIRMED"));

        verify(bookingService, times(1)).bookTicket(any(Booking.class));
    }

    @Test
    void testCancelBooking() throws Exception {
        doNothing().when(bookingService).cancelBooking("Book-1");

        mockMvc.perform(delete("/api/bookings/1"))
                .andExpect(status().isNoContent());

        verify(bookingService, times(1)).cancelBooking("Book-1");
    }

    @Test
    void testGetAllBookings() throws Exception {
        List<Booking> bookings = Arrays.asList(booking);
        when(bookingService.getAllBookings()).thenReturn(bookings);

        mockMvc.perform(get("/api/bookings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].trainNumber").value("12345"));

        verify(bookingService, times(1)).getAllBookings();
    }
}
