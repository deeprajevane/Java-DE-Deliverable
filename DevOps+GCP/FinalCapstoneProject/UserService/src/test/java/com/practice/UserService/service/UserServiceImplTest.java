package com.practice.UserService.service;
import com.practice.UserService.dto.LoginDTO;
import com.practice.UserService.dto.UserDto;
import com.practice.UserService.entity.User;
import com.practice.UserService.exception.InvalidCredentialsException;
import com.practice.UserService.exception.UserNotFoundException;
import com.practice.UserService.repository.UserRepository;
import com.practice.UserService.service.Impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Logger log;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;
    private UserDto testUserDto;
    private LoginDTO testLoginDTO;
    String uuid = UUID.randomUUID().toString();


    @BeforeEach
    void setUp() {
        testUser = new User(uuid, "Test User", "test@example.com", "encodedPassword");
        testUserDto = new UserDto("Test User", "test@example.com", "password123");
        testLoginDTO = new LoginDTO("test@example.com", "password123");
    }
    @Test
    void registerUser_WithNewEmail_ReturnsSavedUser() {
        // Arrange
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // Act
        User result = userService.registerUser(testUserDto);

        // Assert
        assertNotNull(result);
        assertEquals(testUser.getEmail(), result.getEmail());
        verify(userRepository, times(1)).findByEmail(testUserDto.getEmail());
        verify(passwordEncoder, times(1)).encode(testUserDto.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void registerUser_WithExistingEmail_ThrowsException() {
        // Arrange
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(testUser));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.registerUser(testUserDto);
        });

        assertEquals("Email already registered", exception.getMessage());
        verify(userRepository, times(1)).findByEmail(testUserDto.getEmail());
        verify(userRepository, never()).save(any(User.class));
    }
    @Test
    void getAllUsers_WhenUsersExist_ReturnsUserList() {
        // Arrange
        List<User> expectedUsers = Arrays.asList(testUser);
        when(userRepository.findAll()).thenReturn(expectedUsers);

        // Act
        List<User> result = userService.getAllUsers();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testUser.getEmail(), result.get(0).getEmail());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getAllUsers_WhenNoUsers_ReturnsEmptyList() {
        // Arrange
        when(userRepository.findAll()).thenReturn(List.of());

        // Act
        List<User> result = userService.getAllUsers();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userRepository, times(1)).findAll();
    }
    @Test
    void authenticateUser_WithValidCredentials_ReturnsUser() {
        // Arrange
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        // Act
        User result = userService.authenticateUser(testLoginDTO);

        // Assert
        assertNotNull(result);
        assertEquals(testUser.getEmail(), result.getEmail());
        verify(userRepository, times(1)).findByEmail(testLoginDTO.getEmail());
        verify(passwordEncoder, times(1))
                .matches(testLoginDTO.getPassword(), testUser.getPassword());
    }

    @Test
    void authenticateUser_WithInvalidEmail_ThrowsUserNotFoundException() {
        // Arrange
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            userService.authenticateUser(testLoginDTO);
        });

        assertEquals("User not Found with Email: " + testLoginDTO.getEmail(), exception.getMessage());
        verify(userRepository, times(1)).findByEmail(testLoginDTO.getEmail());
        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }

    @Test
    void authenticateUser_WithInvalidPassword_ThrowsInvalidCredentialsException() {
        // Arrange
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        // Act & Assert
        InvalidCredentialsException exception = assertThrows(InvalidCredentialsException.class, () -> {
            userService.authenticateUser(testLoginDTO);
        });

        assertEquals("Invalid password", exception.getMessage());
        verify(userRepository, times(1)).findByEmail(testLoginDTO.getEmail());
        verify(passwordEncoder, times(1))
                .matches(testLoginDTO.getPassword(), testUser.getPassword());
    }
}
