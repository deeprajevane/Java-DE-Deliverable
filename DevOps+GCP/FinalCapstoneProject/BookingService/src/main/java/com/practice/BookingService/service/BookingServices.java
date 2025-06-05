package com.practice.BookingService.service.Impl;

import com.practice.BookingService.model.Booking;

public interface BookingServices {
    public Booking bookTicket(Booking booking);
    public void cancelBooking(Long id);
}
