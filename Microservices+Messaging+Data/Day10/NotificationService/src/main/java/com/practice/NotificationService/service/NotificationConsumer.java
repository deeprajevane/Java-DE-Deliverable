package com.practice.NotificationService.service;

import com.practice.NotificationService.entity.EmployeeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

    private static final Logger logger = LoggerFactory.getLogger(NotificationConsumer.class);

    @KafkaListener(topics = "employee", groupId = "notification-group", containerFactory = "employeeKafkaListenerContainerFactory")
    public void consume(EmployeeEvent event) {
        try{
            logger.info("Received Employee Event: {}", event);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}

