package com.practice.Employee.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {
    private final static Logger log = LoggerFactory.getLogger(Consumer.class.getName());
    @KafkaListener(topics = {"fruits"},groupId = "abc")
    public void consumeMessage(String message){
        log.info(message);
    }
}
