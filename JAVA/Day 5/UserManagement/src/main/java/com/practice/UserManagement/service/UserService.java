package com.practice.UserManagement.service;

import com.practice.UserManagement.dto.UserRequestDto;
import com.practice.UserManagement.dto.UserResponseDto;
import com.practice.UserManagement.exception.UserNotFoundException;
import com.practice.UserManagement.model.Address;
import com.practice.UserManagement.model.Company;
import com.practice.UserManagement.model.User;
import com.practice.UserManagement.repository.AddressRepository;
import com.practice.UserManagement.repository.CompanyRepository;
import com.practice.UserManagement.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Transactional
    public void createUserWithAddressAndCompany() {
        Company company = new Company();
        company.setName("DemoCompany");
        company = companyRepository.save(company);

        User user = new User();
        user.setFirstName("Raj");
        user.setLastName("Sharma");
        user.setEmail("demo@example.com");
        user.setPassword("securepass");
        user.setCompany(company);
        user = userRepository.save(user);

        Address address1 = new Address();
        address1.setAddress("123 Main Street");
        address1.setZipCode("12345");
        address1.setUser(user);

        Address address2 = new Address();
        address2.setAddress("456 Elm Street");
        address2.setZipCode("67890");
        address2.setUser(user);

        addressRepository.saveAll(List.of(address1, address2));


         throw new RuntimeException("Simulating failure");

    }
}
