package com.practice.Train_Inventory_Service.service;

import com.practice.Train_Inventory_Service.entity.Train;
import com.practice.Train_Inventory_Service.exception.InsufficientSeatsException;
import com.practice.Train_Inventory_Service.exception.TrainNotFoundException;
import com.practice.Train_Inventory_Service.repository.TrainRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainInventoryService {

    private final TrainRepository trainRepository;
    private final static Logger log = LoggerFactory.getLogger(TrainInventoryService.class.getName());

    public Train addOrUpdateTrain(Train train) {
        try {
            return trainRepository.save(train);
        } catch (Exception e) {
            log.error("Error while saving or updating train: {}", train, e);

            throw new RuntimeException("Failed to save or update train: " + e.getMessage());
        }

    }

    public List<Train> searchTrains(String source, String destination, LocalDate date) {
        return trainRepository.findBySourceAndDestinationAndTravelDate(source, destination, date);
    }

    public Train searchTrains(String trainNumber) {
        return trainRepository.findById(trainNumber)
                .orElseThrow(() -> new TrainNotFoundException(trainNumber));
    }

    public List<Train> allTrain() {
        try {
            return trainRepository.findAll();
        } catch (Exception e) {
            log.error("Error while retrieving all trains", e);
            throw new RuntimeException("Unable to retrieve train list. " + e.getMessage(), e);
        }
    }


    public void reduceSeats(String trainNumber, int seats) {
        log.info("Event for reduce seat in train {}", trainNumber);
        Train train = trainRepository.findById(trainNumber)
                .orElseThrow(() -> new TrainNotFoundException(trainNumber));

        if (train.getAvailableSeats() < seats) {
            throw new InsufficientSeatsException(trainNumber, seats, train.getAvailableSeats());
        }

        train.setAvailableSeats(train.getAvailableSeats() - seats);
        trainRepository.save(train);
    }

    public void increaseSeats(String trainNumber, int seats) {
        log.info("Event for increase seat in train {}", trainNumber);
        Train train = trainRepository.findById(trainNumber)
                .orElseThrow(() -> new RuntimeException("Train not found"));

        train.setAvailableSeats(train.getAvailableSeats() + seats);
        trainRepository.save(train);
    }

    public void updatedSeats(String trainNumber, int seats) {
        log.info("Event for update seats in train {}",trainNumber);
        Train train = trainRepository.findById(trainNumber)
                .orElseThrow(() -> new RuntimeException("Train not found"));

        train.setAvailableSeats(train.getAvailableSeats() + seats);
        trainRepository.save(train);
    }

    public void saveTrainsFromFile(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType == null) throw new IllegalArgumentException("File type not supported");

        try (InputStream is = file.getInputStream()) {
            if (contentType.contains("csv")) {
                parseCSVAndSave(is);
            } else if (contentType.contains("excel") || contentType.contains("spreadsheetml")) {
                parseExcelAndSave(is);
            } else {
                throw new IllegalArgumentException("Unsupported file format: " + contentType);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading file", e);
        }

    }

    private void parseCSVAndSave(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        boolean headerSkipped = false;

        while ((line = reader.readLine()) != null) {
            if (!headerSkipped) {
                headerSkipped = true;
                continue;
            }

            try {
                String[] data = line.split(",");

                if (data.length < 7) {
                    log.info("Skipping row due to insufficient columns: {}",line);
                    continue;
                }

                LocalDate travelDate;
                try {
                    travelDate = LocalDate.parse(data[6]); // assumes YYYY-MM-DD format
                } catch (Exception e) {
                    log.warn("Invalid date format in row: {}",line);
                    continue;
                }

                Train train = Train.builder()
                        .trainNumber(data[0].trim())
                        .trainName(data[1].trim())
                        .source(data[2].trim())
                        .destination(data[3].trim())
                        .availableSeats(Integer.parseInt(data[4].trim()))
                        .totalSeats(Integer.parseInt(data[5].trim()))
                        .travelDate(travelDate)
                        .build();

                trainRepository.save(train);
            } catch (Exception e) {
                log.warn("Error processing row: {}", line);
                log.info(e.getMessage());
            }
        }
        }

    private void parseExcelAndSave(InputStream is) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(is);
        XSSFSheet sheet = workbook.getSheetAt(0);
        boolean headerSkipped = false;

        for (Row row : sheet) {
            if (!headerSkipped) {
                headerSkipped = true;
                continue;
            }

            Train train = new Train();
            train.setTrainNumber(row.getCell(0).getStringCellValue());
            train.setTrainName(row.getCell(1).getStringCellValue());
            train.setSource(row.getCell(2).getStringCellValue());
            train.setDestination(row.getCell(3).getStringCellValue());
            train.setAvailableSeats((int) row.getCell(4).getNumericCellValue());
            train.setTotalSeats((int) row.getCell(5).getNumericCellValue());
            train.setTravelDate(row.getCell(6).getLocalDateTimeCellValue().toLocalDate());

            trainRepository.save(train);
        }

        workbook.close();
    }


}

