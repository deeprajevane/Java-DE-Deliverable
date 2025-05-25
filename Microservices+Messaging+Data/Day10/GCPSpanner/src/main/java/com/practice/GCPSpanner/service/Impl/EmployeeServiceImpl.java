package com.practice.GCPSpanner.service.Impl;

import com.practice.GCPSpanner.dto.EmployeeDTO;
import com.practice.GCPSpanner.entity.Employee;
import com.practice.GCPSpanner.repository.EmployeeRepository;
import com.practice.GCPSpanner.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private static final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    @Autowired
    EmployeeRepository employeeRepository;


    private final ModelMapper modelMapper;




    /**
     * @param employeeDto
     * @return
     */
    @Override
    public EmployeeDTO create(EmployeeDTO employeeDto) {
        Employee saved = employeeRepository.save(modelMapper.map(employeeDto, Employee.class));
        log.info("Employee added successfully");
        return modelMapper.map(saved, EmployeeDTO.class);

    }

    /**
     * @param id
     * @param employeeDto
     * @return
     */
    @Override
    public EmployeeDTO update(Long id, EmployeeDTO employeeDto) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        modelMapper.map(employeeDto,existing);
        log.info("employee updated successfully");
        return modelMapper.map(employeeRepository.save(existing),EmployeeDTO.class);
    }

    /**
     * @param id
     */
//    @Override
//    public void delete(Long id) {
//        Employee existing = employeeRepository.findById(id)
//                .orElseThrow(()-> new ResourceNotFoundException(" Employee not found"));
//        employeeRepository.deleteById(String.valueOf(id));
//        log.info("Employee deleted successfully");
//
//    }

    /**
     * @param id
     * @return
     */
//    @Override
//    public Optional<EmployeeDTO> get(Long id) {
//        log.info("employee table is fetched");
//        return Optional.ofNullable(employeeRepository.findById(id).map(emp -> modelMapper.map(emp, EmployeeDTO.class))
//                .orElseThrow(() -> new ResourceNotFoundException("Employee not found")));
//    }

    /**
     * @return
     */
    @Override
    public List<EmployeeDTO> getAll() {
        return List.of();
    }


}
