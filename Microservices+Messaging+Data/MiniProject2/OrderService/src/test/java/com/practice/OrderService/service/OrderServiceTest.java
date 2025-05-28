package com.practice.OrderService.service;

import com.practice.OrderService.dto.OrderRequest;
import com.practice.OrderService.dto.OrderResponse;
import com.practice.OrderService.dto.OrderEvent;
import com.practice.OrderService.model.Order;
import com.practice.OrderService.publisher.OrderEventPublisher;
import com.practice.OrderService.repository.OrderRepository;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderEventPublisher publisher;

    @InjectMocks
    private OrderService orderService;

    @Test
    void placeOrder_ShouldSaveOrderAndPublishEventAndReturnResponse() {
        // Given
        UUID id = UUID.randomUUID();
        OrderRequest request = new OrderRequest("Test Product", 3, "user@example.com");

        Order savedOrder = new Order();
        savedOrder.setId(id);
        savedOrder.setProduct("Test Product");
        savedOrder.setQuantity(3);
        savedOrder.setEmail("user@example.com");
        savedOrder.setStatus("CREATED");

        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

        // When
        OrderResponse response = orderService.placeOrder(request);

        // Then
        assertEquals(id, response.getOrderId());
        assertEquals("CREATED", response.getStatus());

        verify(orderRepository).save(any(Order.class));

        ArgumentCaptor<OrderEvent> eventCaptor = ArgumentCaptor.forClass(OrderEvent.class);
        verify(publisher).publishOrderEvent(eventCaptor.capture());

        OrderEvent publishedEvent = eventCaptor.getValue();
        assertEquals(id, publishedEvent.getOrderId());
        assertEquals("Test Product", publishedEvent.getProduct());
        assertEquals(3, publishedEvent.getQuantity());
        assertEquals("user@example.com", publishedEvent.getEmail());
        assertEquals("CREATED", publishedEvent.getStatus());
    }
}
