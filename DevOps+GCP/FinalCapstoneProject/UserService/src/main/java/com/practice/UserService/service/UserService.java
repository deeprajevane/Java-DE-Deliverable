package com.practice.UserService.service;

import com.practice.UserService.dto.LoginDTO;
import com.practice.UserService.dto.UserDto;
import com.practice.UserService.entity.User;

import java.util.List;

public interface UserService {
    User registerUser(UserDto dto);
    List<User> getAllUsers();

    User authenticateUser(LoginDTO loginDto);
}
