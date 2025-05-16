package com.practice.UserManagement.service;

import com.practice.UserManagement.model.Address;
import com.practice.UserManagement.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    public Address addAddress(Address address) {
        return this.addressRepository.save(address);

    }
}
