package com.practice.Train_Inventory_Service.controller;

import com.practice.Train_Inventory_Service.entity.Train;
import com.practice.Train_Inventory_Service.service.TrainInventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/trains")
@RequiredArgsConstructor
public class TrainInventoryController {

    private final TrainInventoryService inventoryService;

    @PostMapping
    public ResponseEntity<Train> addTrain(@RequestBody Train train) {
        return ResponseEntity.ok(inventoryService.addOrUpdateTrain(train));
    }

    @GetMapping("/search")
    public List<Train> searchTrains(
            @RequestParam String source,
            @RequestParam String destination,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate travelDate) {
        return inventoryService.searchTrains(source, destination, travelDate);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadTrainData(@RequestParam("file") MultipartFile file) {
        try {
            inventoryService.saveTrainsFromFile(file);
            return ResponseEntity.ok("Train data uploaded successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error uploading file: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Train>> getAllTrains() {
        List<Train> trains = inventoryService.allTrain();
        return ResponseEntity.ok(trains);
    }


    @GetMapping("/{trainNumber}")
    public ResponseEntity<Train> getTrainByNumber(@PathVariable String trainNumber) {
        Train train = inventoryService.searchTrains(trainNumber);
        return ResponseEntity.ok(train);
    }
}

