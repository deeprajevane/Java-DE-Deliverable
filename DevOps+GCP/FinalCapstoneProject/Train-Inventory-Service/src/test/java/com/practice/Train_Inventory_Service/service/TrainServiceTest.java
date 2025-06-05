package com.practice.Train_Inventory_Service.service;


import com.practice.Train_Inventory_Service.entity.Train;
import com.practice.Train_Inventory_Service.exception.InsufficientSeatsException;
import com.practice.Train_Inventory_Service.exception.TrainNotFoundException;
import com.practice.Train_Inventory_Service.repository.TrainRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainInventoryServiceTest {

    @Mock
    private TrainRepository trainRepository;

    @InjectMocks
    private TrainInventoryService trainService;

    private Train sampleTrain;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleTrain = Train.builder()
                .trainNumber("1234")
                .trainName("Express")
                .source("Delhi")
                .destination("Mumbai")
                .availableSeats(100)
                .totalSeats(100)
                .travelDate(LocalDate.now())
                .build();
    }

    @Test
    void testAddOrUpdateTrain() {
        when(trainRepository.save(sampleTrain)).thenReturn(sampleTrain);
        Train result = trainService.addOrUpdateTrain(sampleTrain);
        assertEquals("1234", result.getTrainNumber());
    }

    @Test
    void testSearchTrainByNumberSuccess() {
        when(trainRepository.findById("1234")).thenReturn(Optional.of(sampleTrain));
        Train result = trainService.searchTrains("1234");
        assertEquals("Express", result.getTrainName());
    }

    @Test
    void testSearchTrainByNumberNotFound() {
        when(trainRepository.findById("1234")).thenReturn(Optional.empty());
        assertThrows(TrainNotFoundException.class, () -> trainService.searchTrains("1234"));
    }

    @Test
    void testReduceSeatsSuccess() {
        when(trainRepository.findById("1234")).thenReturn(Optional.of(sampleTrain));
        trainService.reduceSeats("1234", 10);
        verify(trainRepository).save(argThat(train -> train.getAvailableSeats() == 90));
    }

    @Test
    void testReduceSeatsInsufficient() {
        sampleTrain.setAvailableSeats(5);
        when(trainRepository.findById("1234")).thenReturn(Optional.of(sampleTrain));
        assertThrows(InsufficientSeatsException.class, () -> trainService.reduceSeats("1234", 10));
    }

    @Test
    void testIncreaseSeats() {
        when(trainRepository.findById("1234")).thenReturn(Optional.of(sampleTrain));
        trainService.increaseSeats("1234", 5);
        verify(trainRepository).save(argThat(train -> train.getAvailableSeats() == 105));
    }

    @Test
    void testAllTrains() {
        when(trainRepository.findAll()).thenReturn(List.of(sampleTrain));
        List<Train> trains = trainService.allTrain();
        assertEquals(1, trains.size());
    }

    @Test
    void testSearchTrainsByRoute() {
        LocalDate date = LocalDate.now();
        when(trainRepository.findBySourceAndDestinationAndTravelDate("Delhi", "Mumbai", date))
                .thenReturn(List.of(sampleTrain));
        List<Train> result = trainService.searchTrains("Delhi", "Mumbai", date);
        assertEquals(1, result.size());
    }
}

