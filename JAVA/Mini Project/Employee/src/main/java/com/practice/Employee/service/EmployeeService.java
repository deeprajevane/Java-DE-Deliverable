package com.practice.Employee.service;

import com.practice.Employee.dto.EmployeeDTO;
import com.practice.Employee.exception.FileProcessingException;
import com.practice.Employee.model.Employee;

import java.io.InputStream;
import java.util.List;

public interface EmployeeService {
    EmployeeDTO create(EmployeeDTO employeeDto);
    EmployeeDTO update(Long id, EmployeeDTO employeeDto);
    void delete(Long id);
    EmployeeDTO get(Long id);
    List<EmployeeDTO> getAll();
    void importFromCsv(InputStream inputStream) throws FileProcessingException;
}
