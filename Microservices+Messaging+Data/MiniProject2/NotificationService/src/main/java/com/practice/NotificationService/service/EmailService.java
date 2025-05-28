package com.practice.NotificationService.service;

import com.practice.NotificationService.dto.OrderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    JavaMailSender mailSender;

    public void sendOrderConfirmation(OrderEvent event) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(event.getEmail());
        message.setSubject("Order Confirmation - #" + event.getOrderId());
        message.setText("Hello,\n\nYour order for " + event.getProduct() +
                " (Quantity: " + event.getQuantity() + ") has been " + event.getStatus() + ".\n\nThank you!");
        mailSender.send(message);
    }
}
