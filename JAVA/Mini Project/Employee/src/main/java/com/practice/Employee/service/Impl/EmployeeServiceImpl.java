package com.practice.Employee.service.Impl;

import com.practice.Employee.dto.EmployeeDTO;
import com.practice.Employee.exception.FileProcessingException;
import com.practice.Employee.exception.ResourceNotFoundException;
import com.practice.Employee.model.Employee;
import com.practice.Employee.repository.EmployeeRepository;
import com.practice.Employee.service.EmployeeService;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
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


    @Override
    public EmployeeDTO create(EmployeeDTO employeeDto) {
        Employee saved = repository.save(modelMapper.map(employeeDto, Employee.class));
        return modelMapper.map(saved, EmployeeDTO.class);
    }


    @Override
    public EmployeeDTO update(Long id, EmployeeDTO employeeDto) {
        Employee existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        modelMapper.map(employeeDto,existing);
        return modelMapper.map(repository.save(existing),EmployeeDTO.class);
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
    public ByteArrayOutputStream importEmployeeData(InputStream inputStream, String fileType) throws FileProcessingException {
        List<String[]> rows = new ArrayList<>();

        try {
            if ("csv".equalsIgnoreCase(fileType)) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                    rows = reader.lines().skip(1)
                            .map(line -> line.split(","))
                            .collect(Collectors.toList());
                }
            } else if ("xlsx".equalsIgnoreCase(fileType)) {
                try (Workbook workbook = new XSSFWorkbook(inputStream)) {
                    Sheet sheet = workbook.getSheetAt(0);
                    for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                        Row row = sheet.getRow(i);
                        String[] data = new String[3];
                        data[0] = row.getCell(0).getStringCellValue();
                        data[1] = row.getCell(1).getStringCellValue();
                        data[2] = row.getCell(2).getStringCellValue();
                        rows.add(data);
                    }
                }
            } else {
                throw new FileProcessingException("Unsupported file type: " + fileType);
            }


            Workbook outputWorkbook = new XSSFWorkbook();
            Sheet outputSheet = outputWorkbook.createSheet("Import Results");

            int rowIdx = 0;
            Row header = outputSheet.createRow(rowIdx++);
            header.createCell(0).setCellValue("Name");
            header.createCell(1).setCellValue("Email");
            header.createCell(2).setCellValue("Department");
            header.createCell(3).setCellValue("Status");

            for (String[] data : rows) {
                String name = data[0];
                String email = data[1];
                String dept = data[2];

                String status;
                if (repository.existsByEmail(email)) {
                    status = "Already Exists";
                } else {
                    try{
                    Employee emp = new Employee(null, name, email, dept);
                    repository.save(emp);
                    status = "Employee Added";}
                    catch (ConstraintViolationException ex){
                        status = "Validation Failed: " + ex.getMessage();
                    }
                }

                Row row = outputSheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(name);
                row.createCell(1).setCellValue(email);
                row.createCell(2).setCellValue(dept);
                row.createCell(3).setCellValue(status);
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            outputWorkbook.write(outputStream);
            outputWorkbook.close();

            return outputStream;

        } catch (IOException e) {
            throw new FileProcessingException("Error processing file", e);
        }
    }



}
