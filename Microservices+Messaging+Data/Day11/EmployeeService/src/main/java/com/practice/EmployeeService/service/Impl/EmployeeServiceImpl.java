package com.practice.EmployeeService.service.Impl;

import com.practice.EmployeeService.dto.EmployeeDTO;
import com.practice.EmployeeService.exception.FileProcessingException;
import com.practice.EmployeeService.exception.ResourceNotFoundException;
import com.practice.EmployeeService.model.Employee;
import com.practice.EmployeeService.repository.EmployeeRepository;
import com.practice.EmployeeService.service.EmployeeService;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;
    private final ModelMapper modelMapper;

    private static final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class.getName());


    @Override
    public EmployeeDTO create(EmployeeDTO employeeDto) {
        Employee saved = repository.save(modelMapper.map(employeeDto, Employee.class));
        return modelMapper.map(saved, EmployeeDTO.class);
    }


    @Override
    public EmployeeDTO update(Long id, EmployeeDTO employeeDto) {
        log.info("--- Starting transaction ---");
        Employee existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        log.info("Fetched employee:{} " , existing);
        existing.setName(employeeDto.getName());
        log.info("Modified name (not yet flushed)");
        existing.setEmail(employeeDto.getEmail());
        log.info("Modified Email (not yet flushed)");
        existing.setDepartment(employeeDto.getDepartment());
        log.info("Modified department (not yet flushed)");;
        Employee savedEntity = repository.save(existing);
        log.debug("Called save() - flush may occur here");
        EmployeeDTO updated = modelMapper.map(savedEntity,EmployeeDTO.class);
        log.info("--- Ending transaction (  will auto-flush) ---");;
        return updated;
    }

    @Override
    public void delete(Long id) {
        Employee existing = repository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(" Employee not found"));
        repository.deleteById(id);
    }

    @Override
    public EmployeeDTO get(Long id) {
        return repository.findById(id).map(emp->modelMapper.map(emp,EmployeeDTO.class))
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
    }

    @Override
    public List<EmployeeDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(emp -> modelMapper.map(emp, EmployeeDTO.class))
                .collect(Collectors.toList());
    }


    @Transactional
    @Override
    public String updateWithException(Long id, EmployeeDTO employeeDto) {
        Employee existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        modelMapper.map(employeeDto,existing);
        modelMapper.map(repository.save(existing),EmployeeDTO.class);

        throw new RuntimeException("Rollback performed");

    }




}
