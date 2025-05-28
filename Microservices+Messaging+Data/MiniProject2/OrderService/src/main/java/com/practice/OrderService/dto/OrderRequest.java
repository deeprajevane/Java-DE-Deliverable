package com.practice.OrderService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderRequest {
    private String product;
    private int quantity;
    private String email;
}

