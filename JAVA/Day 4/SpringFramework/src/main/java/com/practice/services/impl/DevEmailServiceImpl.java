package com.practice.services.impl;

import com.practice.services.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class DevEmailServiceImpl extends EmailService {
    public DevEmailServiceImpl(@Value("${email.service.name:Dev Email service}") String serviceName){
        this.serviceName= serviceName;
    }
}
