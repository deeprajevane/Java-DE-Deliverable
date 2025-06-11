package com.practice.UserService.entity;

import com.google.cloud.spring.data.spanner.core.mapping.PrimaryKey;
import com.google.cloud.spring.data.spanner.core.mapping.Table;
import jakarta.validation.constraints.Email;
import lombok.*;

@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @PrimaryKey
    private String id; // UUID as string

    private String name;

    @Email
    private String email;

    private String password;
}
