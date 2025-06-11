package com.practice.BookingService.service;

import com.practice.BookingService.model.Booking;

public interface BookingServices {
    public Booking bookTicket(Booking booking);
    public void cancelBooking(String id);
}
