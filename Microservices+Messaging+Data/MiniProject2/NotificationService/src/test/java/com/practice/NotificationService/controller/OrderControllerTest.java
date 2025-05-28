package com.practice.NotificationService.controller;


import com.practice.NotificationService.dto.OrderEvent;
import com.practice.NotificationService.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmailService emailService;

    @Test
    void sendTestEmail_ReturnsOkAndCallsEmailService() throws Exception {
        mockMvc.perform(get("/test/email")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Email sent"));

        verify(emailService, times(1)).sendOrderConfirmation(argThat(event ->
                event.getOrderId().equals(1L) &&
                        event.getEmail().equals("evanedeepraj@gmail.com") &&
                        event.getProduct().equals("Demo Product") &&
                        event.getQuantity() == 2 &&
                        event.getStatus().equals("CREATED")
        ));
    }
}

