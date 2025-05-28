package com.practice.OrderService.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderEvent {
    private UUID orderId;
    private String product;
    private int quantity;
    private String email;
    private String status;
}

