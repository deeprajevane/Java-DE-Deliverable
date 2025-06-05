package com.practice.Train_Inventory_Service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "trains")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Train {
    @Id
    private String trainNumber;
    private String trainName;
    private String source;
    private String destination;
    private LocalDate travelDate;

    private int totalSeats;
    private int availableSeats;
}

