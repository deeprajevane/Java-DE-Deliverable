package com.practice.NotificationService.service;

import com.practice.NotificationService.dto.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class OrderEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(OrderEventConsumer.class.getName());

    @Autowired
    EmailService emailService;

    @KafkaListener(topics = "order-topic", groupId = "notification-group", containerFactory = "employeeKafkaListenerContainerFactory")
    public void consumeOrder(OrderEvent event) {
        log.info("Received order event: {} ", event);
        emailService.sendOrderConfirmation(event);
    }
}
