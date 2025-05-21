package com.practice.Employee.service;

import com.practice.Employee.dto.EmployeeDTO;
import com.practice.Employee.exception.FileProcessingException;
import com.practice.Employee.exception.ResourceNotFoundException;
import com.practice.Employee.model.Employee;
import com.practice.Employee.repository.EmployeeRepository;
import com.practice.Employee.service.Impl.EmployeeServiceImpl;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository repository;

    private ModelMapper modelMapper;

    private final String csvContent = "name,email,department\nJohn,john@example.com,IT\nJane,jane@example.com,HR";

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

        Employee updateEmployee = new Employee(1L, "Jane", "jane@example.com", "Finance");
        EmployeeDTO updateDto = modelMapper.map(updateEmployee,EmployeeDTO.class);


        assertThrows(ResourceNotFoundException.class,
                () -> service.update(nonExistentId, updateDto));
    }

    @Test
    void testUpdateIfFound() {
        Long ExistentId = 1L;

        Employee employee = new Employee(1L, "Jane", "jane@example.com", "Finance");
        EmployeeDTO existedDto = modelMapper.map(employee,EmployeeDTO.class);

        Employee updateEmployee = new Employee(1L, "Jane Smith", "janesmith@example.com", "Finance");
        EmployeeDTO updateDto = modelMapper.map(updateEmployee,EmployeeDTO.class);

        when(repository.findById(ExistentId)).thenReturn(Optional.of(employee));
        when(repository.save(any(Employee.class))).thenReturn(updateEmployee);


        EmployeeDTO result =service.update(ExistentId,updateDto);


        assertEquals("Jane Smith", result.getName());
        assertEquals("janesmith@example.com", result.getEmail());
        assertEquals("Finance", result.getDepartment());
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
//    void testDeleteIfFound() {
//
//        doNothing().when(repository).deleteById(1L);
//        service.delete(1L);
//
//        verify(repository,times(1)).deleteById(1L);
//    }

//    @Test
//    void testDeleteIfNotFound() {
//
//        when(repository).clone(1L);
//        service.delete(1L);
//
//        assertThrows(RuntimeException,service.delete(1L));;
//    }

    @Test
    void testImportEmployeeData_Csv_AllNewEmployees() throws Exception, FileProcessingException {
        InputStream inputStream = new ByteArrayInputStream(csvContent.getBytes());

        when(repository.existsByEmail(anyString())).thenReturn(false);
        when(repository.save(any(Employee.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ByteArrayOutputStream output = service.importEmployeeData(inputStream, "csv");

        try (Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(output.toByteArray()))) {
            Sheet sheet = workbook.getSheetAt(0);

            assertEquals("Employee Added", sheet.getRow(1).getCell(3).getStringCellValue());
            assertEquals("Employee Added", sheet.getRow(2).getCell(3).getStringCellValue());
        }
    }


    @Test
    void testImportEmployeeData_Xlsx() throws Exception, FileProcessingException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("name");
        header.createCell(1).setCellValue("email");
        header.createCell(2).setCellValue("department");

        Row dataRow = sheet.createRow(1);
        dataRow.createCell(0).setCellValue("Alice");
        dataRow.createCell(1).setCellValue("alice@example.com");
        dataRow.createCell(2).setCellValue("Marketing");

        ByteArrayOutputStream inputXlsx = new ByteArrayOutputStream();
        workbook.write(inputXlsx);
        workbook.close();

        InputStream inputStream = new ByteArrayInputStream(inputXlsx.toByteArray());

        when(repository.existsByEmail("alice@example.com")).thenReturn(false);
        when(repository.save(any(Employee.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ByteArrayOutputStream result = service.importEmployeeData(inputStream, "xlsx");

        try (Workbook resultWorkbook = new XSSFWorkbook(new ByteArrayInputStream(result.toByteArray()))) {
            Sheet resultSheet = resultWorkbook.getSheetAt(0);
            assertEquals("Employee Added", resultSheet.getRow(1).getCell(3).getStringCellValue());
        }
    }





}
