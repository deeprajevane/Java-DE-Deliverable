package com.practice.UserManagement.repository;


import com.practice.UserManagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findByCompanyId(Long companyId); // Find users by company ID
}
