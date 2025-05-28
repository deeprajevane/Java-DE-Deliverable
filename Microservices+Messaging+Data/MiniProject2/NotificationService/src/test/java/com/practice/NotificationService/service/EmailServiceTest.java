package com.practice.NotificationService.service;

import com.practice.NotificationService.dto.OrderEvent;
import com.practice.NotificationService.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EmailServiceTest {

    private JavaMailSender mailSender;
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        mailSender = mock(JavaMailSender.class);
        emailService = new EmailService();
        emailService.mailSender = mailSender; // Manually inject mock
    }

    @Test
    void testSendOrderConfirmation_ShouldSendCorrectEmail() {

        OrderEvent event = new OrderEvent();
        event.setEmail("user@example.com");
        event.setOrderId(1L);
        event.setProduct("Laptop");
        event.setQuantity(1);
        event.setStatus("PLACED");

        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);


        emailService.sendOrderConfirmation(event);


        verify(mailSender, times(1)).send(messageCaptor.capture());
        SimpleMailMessage sentMessage = messageCaptor.getValue();

        assertEquals("user@example.com", sentMessage.getTo()[0]);
        assertEquals("Order Confirmation - #1", sentMessage.getSubject());
        String expectedBody = "Hello,\n\nYour order for Laptop (Quantity: 1) has been PLACED.\n\nThank you!";
        assertEquals(expectedBody, sentMessage.getText());
    }
}
