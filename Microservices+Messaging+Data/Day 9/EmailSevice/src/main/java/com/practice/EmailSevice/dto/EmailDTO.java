package com.practice.EmailSevice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class EmailDTO {
    @NotBlank(message = "email recipients is required")
    private String to;
    @NotBlank(message = "Email Subject is required")
    private String subject;
    @NotEmpty(message = "email body is required")
    private String body;
}
