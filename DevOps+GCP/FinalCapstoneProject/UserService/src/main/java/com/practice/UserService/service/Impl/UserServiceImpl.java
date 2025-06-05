package com.practice.UserService.service.Impl;

import com.practice.UserService.dto.LoginDTO;
import com.practice.UserService.dto.UserDto;
import com.practice.UserService.entity.User;
import com.practice.UserService.exception.InvalidCredentialsException;
import com.practice.UserService.exception.UserNotFoundException;
import com.practice.UserService.repository.UserRepository;
import com.practice.UserService.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class.getName());

    @Override
    public User registerUser(UserDto dto) {
        userRepository.findByEmail(dto.getEmail()).ifPresent(u -> {
            throw new RuntimeException("Email already registered");
        });
        User user = new User(null, dto.getName(), dto.getEmail(), passwordEncoder.encode(dto.getPassword()));
        log.info("User created successfully");
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        log.info("All user Data fetched!!");
        return userRepository.findAll();
    }

    @Override
    public User authenticateUser(LoginDTO loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not Found with Email: "+ loginDto.getEmail()));

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            log.warn("Login Failed for {}",loginDto.getEmail());
            throw new InvalidCredentialsException("Invalid password");
        }
        log.info("Login Successful for {}",loginDto.getEmail());

        return user;
    }
}
