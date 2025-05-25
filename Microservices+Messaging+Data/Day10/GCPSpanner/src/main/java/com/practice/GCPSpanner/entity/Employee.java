package com.practice.GCPSpanner.entity;


import com.google.cloud.spring.data.spanner.core.mapping.PrimaryKey;
import com.google.cloud.spring.data.spanner.core.mapping.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employee")
public class Employee {
    @PrimaryKey
    Long id;
    String name;
    String email;
    String department;

}
