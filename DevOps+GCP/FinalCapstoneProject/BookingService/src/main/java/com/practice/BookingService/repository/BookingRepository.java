package com.practice.BookingService.repository;

import com.google.cloud.spring.data.spanner.repository.SpannerRepository;
import com.practice.BookingService.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends SpannerRepository<Booking, String> {}