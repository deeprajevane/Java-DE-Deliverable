package com.practice.BookingService.controller;

import com.practice.BookingService.model.Booking;
import com.practice.BookingService.payload.JSendResponse;
import com.practice.BookingService.service.Impl.BookingServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingServiceImpl bookingService;

    @PostMapping
    public ResponseEntity<JSendResponse<Booking>> bookTicket(@RequestBody Booking booking) {
        Booking saved = bookingService.bookTicket(booking);
        return new ResponseEntity<>(JSendResponse.success(saved), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<JSendResponse<Void>> cancelBooking(@PathVariable String id) {
        bookingService.cancelBooking(id);
        return ResponseEntity.ok(JSendResponse.success(null));
    }

    @GetMapping
    public ResponseEntity<JSendResponse<List<Booking>>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(JSendResponse.success(bookings));
    }
}
