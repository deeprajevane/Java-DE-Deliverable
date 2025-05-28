package com.practice.OrderService.publisher;

import com.practice.OrderService.dto.OrderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderEventPublisher {
    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public void publishOrderEvent(OrderEvent event) {
        kafkaTemplate.send("order-topic", event);
    }
}
