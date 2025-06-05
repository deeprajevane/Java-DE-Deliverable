package com.practice.UserService.controller;

import com.practice.UserService.dto.LoginDTO;
import com.practice.UserService.dto.UserDto;
import com.practice.UserService.entity.User;
import com.practice.UserService.exception.InvalidCredentialsException;
import com.practice.UserService.exception.UserNotFoundException;
import com.practice.UserService.service.JwtService;
import com.practice.UserService.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserController userController;

    private User testUser;
    private UserDto testUserDto;
    private LoginDTO testLoginDTO;
    UUID uuid = UUID.randomUUID();
    @BeforeEach
    void setUp() {
        testUser = new User();

        testUser.setId(uuid);
        testUser.setName("Test User");
        testUser.setEmail("test@example.com");
        testUser.setPassword("encodedPassword");

        testUserDto = new UserDto(
                "Test User",
                "test@example.com",
                "password123"
        );

        testLoginDTO = new LoginDTO(
                "test@example.com",
                "password123"
        );
    }

    @Test
    void register_ValidUser_ReturnsUser() {

        when(userService.registerUser(any(UserDto.class))).thenReturn(testUser);


        User result = userController.register(testUserDto);


        assertNotNull(result);
        assertEquals(testUser.getEmail(), result.getEmail());
        verify(userService, times(1)).registerUser(any(UserDto.class));
    }

    @Test
    void register_InvalidUser_ThrowsException() {

        UserDto invalidUserDto = new UserDto("", "invalid-email", "");
        when(userService.registerUser(any(UserDto.class)))
                .thenThrow(new RuntimeException("Validation failed"));


        assertThrows(RuntimeException.class, () -> {
            userController.register(invalidUserDto);
        });
    }

    @Test
    void login_ValidCredentials_ReturnsToken() {

        String expectedToken = "generated.jwt.token";
        when(userService.authenticateUser(any(LoginDTO.class))).thenReturn(testUser);
        when(jwtService.generateToken(any(User.class))).thenReturn(expectedToken);


        ResponseEntity<Map<String, String>> response = userController.login(testLoginDTO);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedToken, response.getBody().get("token"));
        assertEquals(testUser.getEmail(), response.getBody().get("email"));
        verify(userService, times(1)).authenticateUser(any(LoginDTO.class));
        verify(jwtService, times(1)).generateToken(any(User.class));
    }

    @Test
    void login_InvalidEmail_ThrowsUserNotFoundException() {

        when(userService.authenticateUser(any(LoginDTO.class)))
                .thenThrow(new UserNotFoundException("User not found"));


        assertThrows(UserNotFoundException.class, () -> {
            userController.login(testLoginDTO);
        });
    }

    @Test
    void login_InvalidPassword_ThrowsInvalidCredentialsException() {

        when(userService.authenticateUser(any(LoginDTO.class)))
                .thenThrow(new InvalidCredentialsException("Invalid password"));


        assertThrows(InvalidCredentialsException.class, () -> {
            userController.login(testLoginDTO);
        });
    }

    @Test
    void getAllUsers_WhenUsersExist_ReturnsUserList() {

        List<User> expectedUsers = Collections.singletonList(testUser);
        when(userService.getAllUsers()).thenReturn(expectedUsers);


        List<User> result = userController.getAllUsers();


        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testUser.getEmail(), result.get(0).getEmail());
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void getAllUsers_WhenNoUsers_ReturnsEmptyList() {

        when(userService.getAllUsers()).thenReturn(Collections.emptyList());


        List<User> result = userController.getAllUsers();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userService, times(1)).getAllUsers();
    }
}
