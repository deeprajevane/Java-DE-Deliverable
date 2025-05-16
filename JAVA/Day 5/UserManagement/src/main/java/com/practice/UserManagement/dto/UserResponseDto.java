package com.practice.UserManagement.dto;

import lombok.Data;

import java.util.List;


@Data
public class UserResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Long companyId;
    private List<AddressDto> addresses;
}

