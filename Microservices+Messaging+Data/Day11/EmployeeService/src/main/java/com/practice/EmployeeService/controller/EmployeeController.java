package com.practice.EmployeeService.controller;

import com.practice.EmployeeService.dto.EmployeeDTO;
import com.practice.EmployeeService.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService service;

    private final static Logger log = LoggerFactory.getLogger(EmployeeController.class.getName());

    @PostMapping
    public ResponseEntity<EmployeeDTO> create(@RequestBody @Valid EmployeeDTO dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> get(@PathVariable Long id) {
        log.info("Employee fetched with id: {}",id);
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAll() {
        log.info("List of Employees Fetched");
        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> update(@PathVariable Long id, @RequestBody @Valid EmployeeDTO employeeDto) {
        log.info("Employees Updated successfully");
        return ResponseEntity.ok(service.update(id, employeeDto));
    }

    @PutMapping("/updateWithException/{id}")
    public ResponseEntity<String> updateWithException(@PathVariable Long id, @RequestBody @Valid EmployeeDTO employeeDto) {
        log.info("Demo @Transactional ");
        return ResponseEntity.ok(service.updateWithException(id, employeeDto));
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.delete(id);
        log.info("Employee Deleted with id, {}",id);
        return ResponseEntity.ok("Employee Deleted successfully");
    }


}
