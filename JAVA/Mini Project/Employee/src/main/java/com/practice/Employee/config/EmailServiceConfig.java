package com.practice.Employee.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class EmailServiceConfig {

    @Value("${email.service.url}")
    private String emailServiceUrl;

}

