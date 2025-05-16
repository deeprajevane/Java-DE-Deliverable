package com.practice.UserManagement.dto;

import com.practice.UserManagement.model.User;
import lombok.Data;

@Data
public class AddressDto {
    private long id;
    private String address;
    private String zipcode;
    private User user;
}
