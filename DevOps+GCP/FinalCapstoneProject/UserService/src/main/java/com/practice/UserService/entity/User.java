package com.practice.UserService.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.UUID;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;


    @Column(name = "name",nullable = false)
    private String name;

    @Column(unique = true,name = "email")
    @Email
    private String email;

    @Column(name = "password",nullable = false, length = 60)
    private String password;
}
