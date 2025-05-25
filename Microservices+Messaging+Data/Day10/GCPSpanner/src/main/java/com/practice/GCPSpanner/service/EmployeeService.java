package com.practice.GCPSpanner.service;

import com.practice.GCPSpanner.dto.EmployeeDTO;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    EmployeeDTO create(EmployeeDTO employeeDto);
    EmployeeDTO update(Long id, EmployeeDTO employeeDto);
//    void delete(Long id);
//    Optional<EmployeeDTO> get(Long id);
    List<EmployeeDTO> getAll();
}
