package com.practice.Employee.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.Employee.dto.EmployeeDTO;
import com.practice.Employee.exception.FileProcessingException;
import com.practice.Employee.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateEmployee() throws Exception {
        EmployeeDTO dto = new EmployeeDTO(1L, "John", "john@example.com","Developer" );
        when(employeeService.create(any(EmployeeDTO.class))).thenReturn(dto);

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(dto.getId()))
                .andExpect(jsonPath("$.name").value(dto.getName()));
    }

    @Test
    void testGetEmployeeById() throws Exception {
        EmployeeDTO dto = new EmployeeDTO(1L, "John", "Developer", "john@example.com");
        when(employeeService.get(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"));
    }

    @Test
    void testGetAllEmployees() throws Exception {
        List<EmployeeDTO> list = Arrays.asList(
                new EmployeeDTO(1L, "John", "Dev", "john@example.com"),
                new EmployeeDTO(2L, "Jane", "QA", "jane@example.com")
        );
        when(employeeService.getAll()).thenReturn(list);

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    void testUpdateEmployee() throws Exception {
        EmployeeDTO updated = new EmployeeDTO(1L, "John Updated",  "john.updated@example.com","Manager");
        when(employeeService.update(eq(1L), any(EmployeeDTO.class))).thenReturn(updated);

        mockMvc.perform(put("/api/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Updated"));
    }

//    @Test
//    void testDeleteEmployee() throws Exception {
//        doNothing().when(employeeService).delete(1L);
//
//        mockMvc.perform(delete("/api/employees/1"))
//                .andExpect(status().isNoContent());
//    }

    @Test
    void testImportEmployee_Success() throws Exception, FileProcessingException {

        byte[] sampleBytes = "processed data".getBytes();
        ByteArrayOutputStream mockOutput = new ByteArrayOutputStream();
        mockOutput.write(sampleBytes);

        MockMultipartFile mockFile = new MockMultipartFile(
                "file", "employees.csv", "text/csv", "name,email,department\nJohn,john@example.com,IT".getBytes()
        );

        when(employeeService.importEmployeeData(any(InputStream.class), eq("csv"))).thenReturn(mockOutput);


        mockMvc.perform(multipart("/api/employees/import").file(mockFile))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=result.xlsx"))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, "application/octet-stream"))
                .andExpect(content().bytes(sampleBytes));
    }

    @Test
    void testImportEmployee_ThrowsFileProcessingException() throws Exception, FileProcessingException {
        MockMultipartFile mockFile = new MockMultipartFile(
                "file", "employees.csv", "text/csv", "name,email,department\nJohn,john@example.com,IT".getBytes()
        );

        when(employeeService.importEmployeeData(any(InputStream.class), eq("csv")))
                .thenThrow(new FileProcessingException("Processing failed", new RuntimeException()));

        mockMvc.perform(multipart("/api/employees/import").file(mockFile))
                .andExpect(status().isInternalServerError()); // Only if you have exception handling
    }




}