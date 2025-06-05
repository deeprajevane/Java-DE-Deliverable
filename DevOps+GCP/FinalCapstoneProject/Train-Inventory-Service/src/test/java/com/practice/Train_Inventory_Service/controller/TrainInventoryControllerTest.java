package com.practice.Train_Inventory_Service.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.Train_Inventory_Service.entity.Train;
import com.practice.Train_Inventory_Service.service.TrainInventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TrainInventoryController.class)
public class TrainInventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrainInventoryService inventoryService;

    @Autowired
    private ObjectMapper objectMapper;

    private Train train;

    @BeforeEach
    public void setup() {
        train = Train.builder()
                .trainNumber("12345")
                .trainName("Express")
                .source("Delhi")
                .destination("Mumbai")
                .availableSeats(100)
                .totalSeats(150)
                .travelDate(LocalDate.of(2025, 6, 25))
                .build();
    }

    @Test
    void testAddTrain() throws Exception {
        when(inventoryService.addOrUpdateTrain(any(Train.class))).thenReturn(train);

        mockMvc.perform(post("/api/trains")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(train)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.trainNumber").value("12345"));
    }

    @Test
    void testSearchTrains() throws Exception {
        when(inventoryService.searchTrains("Delhi", "Mumbai", LocalDate.of(2025, 6, 25)))
                .thenReturn(List.of(train));

        mockMvc.perform(get("/api/trains/search")
                        .param("source", "Delhi")
                        .param("destination", "Mumbai")
                        .param("travelDate", "2025-06-25"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].trainNumber").value("12345"));
    }

    @Test
    void testUploadTrainData() throws Exception {
        MockMultipartFile mockFile = new MockMultipartFile(
                "file", "trains.csv", "text/csv", "header\n".getBytes());

        doNothing().when(inventoryService).saveTrainsFromFile(any());

        mockMvc.perform(multipart("/api/trains/upload").file(mockFile))
                .andExpect(status().isOk())
                .andExpect(content().string("Train data uploaded successfully!"));
    }

    @Test
    void testGetAllTrains() throws Exception {
        when(inventoryService.allTrain()).thenReturn(List.of(train));

        mockMvc.perform(get("/api/trains"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].trainNumber").value("12345"));
    }

    @Test
    void testGetTrainByNumber() throws Exception {
        when(inventoryService.searchTrains("12345")).thenReturn(train);

        mockMvc.perform(get("/api/trains/12345"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.trainNumber").value("12345"));
    }
}

