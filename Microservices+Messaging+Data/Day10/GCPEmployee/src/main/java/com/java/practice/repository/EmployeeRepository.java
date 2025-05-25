package com.java.practice.repository;

import com.google.cloud.spring.data.spanner.repository.SpannerRepository;
import com.java.practice.model.Employee;

public interface EmployeeRepository extends SpannerRepository<Employee,Long> {
}
