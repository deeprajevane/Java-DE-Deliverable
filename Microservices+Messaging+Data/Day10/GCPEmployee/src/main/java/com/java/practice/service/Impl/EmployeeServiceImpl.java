package com.java.practice.service.Impl;

import com.java.practice.exception.EmployeeNotFoundException;
import com.java.practice.model.Employee;
import com.java.practice.repository.EmployeeRepository;
import com.java.practice.service.EmployeeService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerFactoryFriend;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final ModelMapper modelMapper;

    private final static org.slf4j.Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class.getName());


    @Override
    public Employee createEmployee(Employee employee) {
        log.info("Employee is created");
        return employeeRepository.save(employee);
    }


    @Override
    public Employee getEmployee(Long id) {
        log.info("Employee is fetched with id:{}",id);
        return employeeRepository.findById(id)
                .orElseThrow(()-> new EmployeeNotFoundException("Employee not Found"));
    }

    @Override
    public Employee updateEmployee(Long id,Employee employee) {
        log.info("Employee is updated with id:{}",id);
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
        modelMapper.map(employee,existing);
        return employeeRepository.save(existing);
    }

    @Override
    public void deleteEmployee(Long id) {
        log.info("Employee is deleted with id:{}",id);
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
        employeeRepository.deleteById(id);
    }

    @Override
    public List<Employee> getAllEmployee() {
        return (List<Employee>) employeeRepository.findAll();
    }
}
