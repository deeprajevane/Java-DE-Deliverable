package com.practice.GCPSpanner.controller;


import com.practice.GCPSpanner.config.GcpProperties;
import com.practice.GCPSpanner.dto.EmployeeDTO;
import com.practice.GCPSpanner.entity.Employee;
import com.practice.GCPSpanner.repository.EmployeeRepository;
import com.practice.GCPSpanner.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    @Autowired
    EmployeeService service;

    @Autowired
    EmployeeRepository repository;

    private final GcpProperties gcpProperties;

    private final static Logger log = LoggerFactory.getLogger(EmployeeController.class.getName());

    @GetMapping("/hello")
    public ResponseEntity<String> test(){
        log.info("GCP Project ID: {}", gcpProperties.getProjectId());
        log.info("GCP Instance ID: {}", gcpProperties.getInstanceId());
        log.info("GCP Database: {}", gcpProperties.getDatabase());
        log.info("GCP Credentials Location: {}", gcpProperties.getCredentials().getLocation());

        return ResponseEntity.ok("Something is working");
    }


   @PostMapping
    public ResponseEntity<String> create(@RequestBody @Valid EmployeeDTO dto) {
        repository.save(new Employee(
                dto.getId(), dto.getName(), dto.getEmail(), dto.getDepartment()
        ));

        return ResponseEntity.ok("Employee Created");
    }

    @GetMapping("/testCreate/{name}")
    public ResponseEntity<String> createTest(@PathVariable String name) {
        repository.save(new Employee(
                1L,
                name,
                "unique@gmail.com",
                "HR"
        ));

        return ResponseEntity.ok("Employee Created");
    }

    @GetMapping("/test-connection")
    public String testConnection() {
        try {
            // Simple query to verify connection
            long count = repository.count();
            return "Successfully connected to Cloud Spanner! Total employees: " + count;
        } catch (Exception e) {
            return "Connection failed: " + e.getMessage();
        }
    }


//    @GetMapping("/{id}")
//    public ResponseEntity<EmployeeDTO> get(@PathVariable Long id) {
//        log.info("Employee fetched with id: {}", id);
//        return service.get(id)
//                .map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    @GetMapping
//    public ResponseEntity<List<EmployeeDTO>> getAll() {
//        log.info("List of Employees Fetched");
//        return ResponseEntity.ok(service.getAll());
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<EmployeeDTO> update(@PathVariable Long id, @RequestBody @Valid EmployeeDTO employeeDto) {
//        log.info("Employees Updated successfully");
//        return ResponseEntity.ok(service.update(id, employeeDto));
//    }
//
//
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> delete(@PathVariable Long id) {
//        service.delete(id);
//        log.info("Employee Deleted");
//        return ResponseEntity.ok("Employee Deleted successfully");
//    }


}
