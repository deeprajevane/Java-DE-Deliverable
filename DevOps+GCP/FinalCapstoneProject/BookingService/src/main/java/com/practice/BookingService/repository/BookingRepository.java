package com.practice.BookingService.repository;

import com.practice.BookingService.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {}