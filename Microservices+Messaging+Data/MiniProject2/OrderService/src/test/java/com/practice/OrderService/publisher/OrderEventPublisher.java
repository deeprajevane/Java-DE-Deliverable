package com.practice.OrderService.publisher;

import static org.mockito.Mockito.verify;

import com.practice.OrderService.dto.OrderEvent;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.kafka.core.KafkaTemplate;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class OrderEventPublisherTest {

    @Mock
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @InjectMocks
    private OrderEventPublisher publisher;

    @Test
    void publishOrderEvent_ShouldSendEventToKafkaTopic() {

        OrderEvent event = new OrderEvent();
        event.setOrderId(java.util.UUID.randomUUID());
        event.setProduct("Test Product");
        event.setQuantity(5);
        event.setEmail("test@example.com");
        event.setStatus("CREATED");


        publisher.publishOrderEvent(event);


        ArgumentCaptor<String> topicCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<OrderEvent> eventCaptor = ArgumentCaptor.forClass(OrderEvent.class);

        verify(kafkaTemplate).send(topicCaptor.capture(), eventCaptor.capture());

        assertEquals("order-topic", topicCaptor.getValue());
        assertEquals(event, eventCaptor.getValue());
    }
}

