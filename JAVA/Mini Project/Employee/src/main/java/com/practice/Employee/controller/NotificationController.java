package com.practice.Employee.controller;

import com.practice.Employee.model.EmployeeEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees-notification")
public class NotificationController {

    @Autowired
    KafkaTemplate kafkaTemplate;

    @GetMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestParam String message){
        kafkaTemplate.send("fruits",message);
        return ResponseEntity.ok("Added message to topic");
    }


    @PostMapping("/create")
    public ResponseEntity<String> createEmployeeEvent(@RequestBody EmployeeEvent employeeEvent){
        kafkaTemplate.send("employee",employeeEvent);

        return ResponseEntity.ok("Added Employee event");
    }


}
