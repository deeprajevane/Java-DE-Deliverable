package com.practice.EmployeeService.service;

import com.practice.EmployeeService.dto.EmployeeDTO;
import com.practice.EmployeeService.exception.FileProcessingException;
import jakarta.transaction.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

public interface EmployeeService {
    EmployeeDTO create(EmployeeDTO employeeDto);
    EmployeeDTO update(Long id, EmployeeDTO employeeDto);
    void delete(Long id);
    EmployeeDTO get(Long id);
    List<EmployeeDTO> getAll();

    @Transactional
    String updateWithException(Long id, EmployeeDTO employeeDto);

   }
