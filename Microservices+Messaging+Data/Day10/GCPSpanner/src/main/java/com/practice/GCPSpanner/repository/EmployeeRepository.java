package com.practice.GCPSpanner.repository;

import com.google.cloud.spring.data.spanner.repository.SpannerRepository;
import com.practice.GCPSpanner.entity.Employee;


public interface EmployeeRepository extends SpannerRepository<Employee,Long> {
    boolean existsById(Long id);


}
