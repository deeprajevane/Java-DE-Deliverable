package com.practice.Employee.controller;


import com.practice.Employee.config.EmailServiceConfig;
import com.practice.Employee.dto.EmailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/email")
public class EmailController {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    EmailServiceConfig emailServiceConfig;



    @PostMapping("/email-details")
    public ResponseEntity<String> addEmailDetails(@RequestBody String name) {
        String baseUrl = emailServiceConfig.getEmailServiceUrl();
        EmailDTO email = new EmailDTO();
        email.setTo("employee@example.com");
        email.setSubject("Welcome " + name);
        email.setBody("Hello " + name + ", welcome!");

        restTemplate.postForEntity(baseUrl+"/email", email, String.class);

        return ResponseEntity.ok("Employee added and email sent via RestTemplate!");
    }
}
