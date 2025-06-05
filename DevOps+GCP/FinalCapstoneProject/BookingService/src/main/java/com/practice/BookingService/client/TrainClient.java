package com.practice.BookingService.client;


import com.practice.BookingService.dto.Train;
import com.practice.BookingService.dto.TrainBookingEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TrainClient {

    private final RestTemplate restTemplate;

    @Value("${train.inventory.service.url}")
    private String trainInventoryBaseUrl;

    public TrainClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Train getTrainByNumber(String trainNumber) {
        String url = trainInventoryBaseUrl + "/api/trains/" + trainNumber;
        return restTemplate.getForObject(url, Train.class);
    }
}
