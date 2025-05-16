package com.practice.UserManagement.repository;

import com.practice.UserManagement.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    boolean existsByName(String name);
}

