package com.practice.UserManagement.repository;

import com.practice.UserManagement.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByUserId(Long userId);
}
