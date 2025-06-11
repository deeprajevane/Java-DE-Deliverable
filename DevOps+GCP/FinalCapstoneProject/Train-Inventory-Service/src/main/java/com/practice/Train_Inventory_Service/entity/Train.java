package com.practice.Train_Inventory_Service.entity;

import com.google.cloud.spring.data.spanner.core.mapping.PrimaryKey;


import com.google.cloud.spring.data.spanner.core.mapping.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Table(name = "trains")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Train {
    @PrimaryKey
    private String trainNumber;
    private String trainName;
    private String source;
    private String destination;
    private LocalDate travelDate;

    private int totalSeats;
    private int availableSeats;
}

