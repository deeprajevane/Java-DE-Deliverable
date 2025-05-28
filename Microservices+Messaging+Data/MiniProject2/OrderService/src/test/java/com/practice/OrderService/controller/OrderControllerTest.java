package com.practice.OrderService.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.OrderService.dto.OrderRequest;
import com.practice.OrderService.dto.OrderResponse;
import com.practice.OrderService.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createOrder_ReturnsOrderResponse() throws Exception {
        // Given
        UUID id = UUID.randomUUID();
        OrderRequest request = new OrderRequest("product-123", 2, "evanedeepraj@gmail.com");
        OrderResponse response = new OrderResponse(id, "CREATED");

        when(orderService.placeOrder(any(OrderRequest.class))).thenReturn(response);

        // When/Then
        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(id.toString()))
                .andExpect(jsonPath("$.status").value("CREATED"));
    }
}
