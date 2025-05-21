package com.practice.EmailSevice.controller;

import com.practice.EmailSevice.dto.EmailDTO;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailController {

    private final static Logger log = LoggerFactory.getLogger(EmailController.class.getName());

    @PostMapping
    public ResponseEntity<String> sendEmail(@RequestBody @Valid EmailDTO emailDTO){
            log.info("Email sent to {}",emailDTO.getTo());
            return ResponseEntity.ok("Email sent");
    }
}
