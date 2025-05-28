package com.practice.NotificationService.controller;

import com.practice.NotificationService.dto.OrderEvent;
import com.practice.NotificationService.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/test")
public class OrderController {

    @Autowired
    private EmailService emailService;


    @GetMapping("/email")
    public ResponseEntity<String> sendTestEmail() {
        OrderEvent dummy = new OrderEvent(1L, "Demo Product", 2, "evanedeepraj@gmail.com", "CREATED");
        emailService.sendOrderConfirmation(dummy);
        return ResponseEntity.ok("Email sent");
    }
}
