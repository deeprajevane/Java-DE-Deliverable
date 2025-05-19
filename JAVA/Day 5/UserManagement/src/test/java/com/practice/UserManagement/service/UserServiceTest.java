package com.practice.UserManagement.service;

import com.practice.UserManagement.model.Address;
import com.practice.UserManagement.model.Company;
import com.practice.UserManagement.model.User;
import com.practice.UserManagement.repository.CompanyRepository;
import com.practice.UserManagement.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.util.Optional;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private CompanyRepository companyRepository;
    @SuppressWarnings("removal")
    @MockBean
    private UserRepository userRepository;

    @Test
    public void testGetUserById() {
//        User mockUser = new User(1L, "John Doe", "john@example.com");
        Company company = new Company();
        company.setName("DemoCompany");
        company = companyRepository.save(company);

        User mockUser = new User();
        mockUser.setFirstName("Raj");
        mockUser.setLastName("Sharma");
        mockUser.setEmail("demo@example.com");
        mockUser.setPassword("securepass");
        mockUser.setCompany(company);
        mockUser = userRepository.save(mockUser);
        Address address1 = new Address();
        address1.setAddress("123 Main Street");
        address1.setZipCode("12345");
        address1.setUser(mockUser);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        User user = userService.getUserById(1L);

        Assertions.assertNotNull(user);
        Assertions.assertEquals("Raj", user.getFirstName());
    }
}
