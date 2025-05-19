package com.practice.Employee.service;

import com.practice.Employee.dto.EmployeeDTO;
import com.practice.Employee.exception.ResourceNotFoundException;
import com.practice.Employee.model.Employee;
import com.practice.Employee.repository.EmployeeRepository;
import com.practice.Employee.service.Impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository repository;

    private ModelMapper modelMapper;

    @InjectMocks
    private EmployeeServiceImpl service;

    @BeforeEach
    void setup() {
        modelMapper = new ModelMapper();
        service = new EmployeeServiceImpl(repository, modelMapper);
    }

    @Test
    void testCreateEmployee() {
        EmployeeDTO inputDto = new EmployeeDTO(null, "John", "john@example.com", "HR");
        Employee inputEntity  = modelMapper.map(inputDto, Employee.class);

        Employee savedEntity = new Employee(1L, "John", "john@example.com", "HR");
        EmployeeDTO expectedDto = modelMapper.map(savedEntity, EmployeeDTO.class);

        when(repository.save(inputEntity )).thenReturn(savedEntity);

        EmployeeDTO result = service.create(inputDto);

        assertEquals(expectedDto.getName(), result.getName());
        assertEquals(expectedDto.getEmail(), result.getEmail());
        assertEquals(expectedDto.getDepartment(), result.getDepartment());
    }

    @Test
    void testUpdateThrowsIfNotFound() {
        Long nonExistentId = 1L;

        when(repository.findById(nonExistentId)).thenReturn(Optional.empty());

        EmployeeDTO updateDto = new EmployeeDTO(1L, "Jane", "jane@example.com", "Finance");

        assertThrows(ResourceNotFoundException.class,
                () -> service.update(nonExistentId, updateDto));
    }
    @Test
    void testGet() {
        Employee emp = new Employee(1L, "Alice", "alice@example.com", "IT");
        when(repository.findById(1L)).thenReturn(Optional.of(emp));

        EmployeeDTO dto = service.get(1L);

        assertEquals("Alice", dto.getName());
        assertEquals("alice@example.com", dto.getEmail());
    }

    @Test
    void testGetAll() {
        List<Employee> list = Arrays.asList(
                new Employee(1L, "A", "a@a.com", "X"),
                new Employee(2L, "B", "b@b.com", "Y")
        );

        when(repository.findAll()).thenReturn(list);

        List<EmployeeDTO> all = service.getAll();

        assertEquals(2, all.size());
    }

//    @Test
//    void testDelete() {
//        Employee emp = new Employee(1L, "A", "a@a.com", "IT");
//        when(repository.findById(1L)).thenReturn(Optional.of(emp));
//
//        service.delete(1L);
//
//        verify(repository).deleteById(1L);
//    }
}
