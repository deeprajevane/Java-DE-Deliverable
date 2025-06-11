package com.practice.UserService.repository;


import com.google.cloud.spring.data.spanner.repository.SpannerRepository;
import com.practice.UserService.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends SpannerRepository<User, String> {
    Optional<User> findByEmail(String email);
}
