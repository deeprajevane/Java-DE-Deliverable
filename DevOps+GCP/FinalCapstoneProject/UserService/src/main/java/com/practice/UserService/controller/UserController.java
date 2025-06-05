package com.practice.UserService.controller;

import com.practice.UserService.dto.LoginDTO;
import com.practice.UserService.dto.UserDto;
import com.practice.UserService.entity.User;
import com.practice.UserService.service.JwtService;
import com.practice.UserService.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public User register(@Valid @RequestBody UserDto dto) {
        return userService.registerUser(dto);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginDTO loginDto) {
        User user = userService.authenticateUser(loginDto);
        String token = jwtService.generateToken(user);
        return ResponseEntity.ok(Map.of(
                "token", token,
                "email", user.getEmail()
        ));
    }
}
