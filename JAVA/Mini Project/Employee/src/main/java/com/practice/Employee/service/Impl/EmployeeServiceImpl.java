package com.practice.Employee.service.Impl;

import com.practice.Employee.dto.EmployeeDTO;
import com.practice.Employee.exception.FileProcessingException;
import com.practice.Employee.exception.ResourceNotFoundException;
import com.practice.Employee.model.Employee;
import com.practice.Employee.repository.EmployeeRepository;
import com.practice.Employee.service.EmployeeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public EmployeeDTO create(EmployeeDTO employeeDto) {
        Employee saved = repository.save(modelMapper.map(employeeDto, Employee.class));
        return modelMapper.map(saved, EmployeeDTO.class);
    }

    @Transactional
    @Override
    public EmployeeDTO update(Long id, EmployeeDTO employeeDto) {
        Employee existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        modelMapper.map(employeeDto,existing);
        return modelMapper.map(repository.save(existing),EmployeeDTO.class);
    }

    @Override
    public void delete(Long id) {
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

    @Override
    public void importFromCsv(InputStream inputStream) throws FileProcessingException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            reader.lines().skip(1).forEach(line -> {
                String[] data = line.split(",");
                Employee emp = new Employee(null, data[0], data[1], data[2]);
                repository.save(emp);
            });
        } catch (IOException e) {
            throw new FileProcessingException("Error processing file", e);
        }
    }
}
