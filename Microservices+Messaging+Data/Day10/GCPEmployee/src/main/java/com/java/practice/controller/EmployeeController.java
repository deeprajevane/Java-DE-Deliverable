package com.java.practice.controller;


import com.java.practice.model.Employee;
import com.java.practice.repository.EmployeeRepository;
import com.java.practice.service.EmployeeService;
import com.java.practice.service.Impl.EmployeeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;

    private final static Logger log = LoggerFactory.getLogger(EmployeeController.class.getName());


    @GetMapping("/test-connection")
    public String testConnection() {
        try {
            // Simple query to verify connection
            long count = employeeRepository.count();
            return "Successfully connected to Cloud Spanner! Total employees: " + count;
        } catch (Exception e) {
            return "Connection failed: " + e.getMessage();
        }
    }


    @PostMapping("/create")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee created = employeeService.createEmployee(employee);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/fetch/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployee(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, employee));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee Deleted Successfully");
    }


    @GetMapping
    public ResponseEntity<List<Employee>> getAll() {
        log.info("List of Employees Fetched");
        return ResponseEntity.ok(employeeService.getAllEmployee());
    }







}
