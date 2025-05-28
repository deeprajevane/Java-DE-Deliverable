package com.practice.NotificationService.service;

import com.practice.NotificationService.dto.OrderEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.Mockito.*;

class OrderEventConsumerTest {

    private OrderEventConsumer consumer;
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        emailService = mock(EmailService.class);
        consumer = new OrderEventConsumer();
        consumer.emailService = emailService;
    }

    @Test
    void testConsumeOrder_ShouldCallEmailService() {
        OrderEvent event = new OrderEvent();
        UUID id = UUID.randomUUID();
        event.setOrderId(id);
        event.setStatus("PLACED");
        event.setEmail("test@example.com");

        consumer.consumeOrder(event);

        verify(emailService, times(1)).sendOrderConfirmation(event);
    }

}
