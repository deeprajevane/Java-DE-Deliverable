package com.practice.NotificationService.service;

import com.practice.NotificationService.dto.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    JavaMailSender mailSender;

    private final static Logger log = LoggerFactory.getLogger(EmailService.class.getName());

    public void sendOrderConfirmation(OrderEvent event) {


        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(event.getEmail());
        message.setSubject("Order Confirmation - #" + event.getOrderId());
        message.setText("Hello,\n\nYour order for " + event.getProduct() +
                " (Quantity: " + event.getQuantity() + ") has been " + event.getStatus() + ".\n\nThank you!");
        try {
            mailSender.send(message);
            log.info("Order confirmation email sent successfully to: {} " , event.getEmail());
        } catch (MailException e) {

            log.info("Failed to send order confirmation email to:{} : {} ", event.getEmail() , e.getMessage());

        }
    }
}
