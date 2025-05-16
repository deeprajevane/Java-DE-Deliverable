package com.practice.UserManagement.controller;


import com.practice.UserManagement.dto.UserRequestDto;
import com.practice.UserManagement.dto.UserResponseDto;
import com.practice.UserManagement.model.User;
import com.practice.UserManagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/addDemo")
    public ResponseEntity<String> runDemo() {
        userService.createUserWithAddressAndCompany();
        return ResponseEntity.ok("Transaction completed");
    }
}