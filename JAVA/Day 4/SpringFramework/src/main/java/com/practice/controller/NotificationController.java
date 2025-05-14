package com.practice.controller;

import com.practice.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {

    private final NotificationService emailService;
    private final String profileMessage;

    @Value("${notification.default.recipient:user@example.com}")
    private String defaultRecipient;

    @Autowired
    public NotificationController(
            NotificationService emailService,
            String profileMessage) {
        this.emailService = emailService;
        this.profileMessage = profileMessage;
    }

    public void sendEmailNotification() {
        emailService.sendNotification(defaultRecipient, "Hello from Spring!");
        System.out.println(profileMessage);
    }

}
