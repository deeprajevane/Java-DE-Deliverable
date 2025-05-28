package com.practice.OrderService.service;

import com.practice.OrderService.dto.OrderEvent;
import com.practice.OrderService.dto.OrderRequest;
import com.practice.OrderService.dto.OrderResponse;
import com.practice.OrderService.model.Order;
import com.practice.OrderService.publisher.OrderEventPublisher;
import com.practice.OrderService.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderEventPublisher publisher;

    public OrderResponse placeOrder(OrderRequest request) {
        Order order = new Order();
        order.setProduct(request.getProduct());
        order.setQuantity(request.getQuantity());
        order.setEmail(request.getEmail());
        order.setStatus("CREATED");

        Order savedOrder = orderRepository.save(order);

        OrderEvent event = new OrderEvent();
        event.setOrderId(savedOrder.getId());
        event.setProduct(savedOrder.getProduct());
        event.setQuantity(savedOrder.getQuantity());
        event.setEmail(savedOrder.getEmail());
        event.setStatus(savedOrder.getStatus());

        publisher.publishOrderEvent(event);

        return new OrderResponse(savedOrder.getId(), savedOrder.getStatus());
    }
}
