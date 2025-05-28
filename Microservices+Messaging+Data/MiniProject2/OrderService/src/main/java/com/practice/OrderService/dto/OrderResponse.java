package com.practice.OrderService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class OrderResponse {
    private UUID orderId;
    private String status;
}

