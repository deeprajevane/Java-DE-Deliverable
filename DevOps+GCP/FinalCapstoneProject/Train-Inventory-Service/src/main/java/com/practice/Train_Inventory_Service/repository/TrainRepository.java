package com.practice.Train_Inventory_Service.repository;

import com.google.cloud.spring.data.spanner.repository.SpannerRepository;
import com.practice.Train_Inventory_Service.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TrainRepository extends SpannerRepository<Train, String> {
    List<Train> findBySourceAndDestinationAndTravelDate(
            String source, String destination, LocalDate travelDate);
    Train findByTrainNumber(String TrainNumber);
}

