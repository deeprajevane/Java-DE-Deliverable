import java.io.*;
import java.util.*;
import java.util.stream.*;

public class CsvParser {

    public static List<Employee> parseEmployees(File inputFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            return reader.lines()
                    .skip(1)
                    .map(Employee::parse)
                    .collect(Collectors.toList());
        }
    }

    public static void writeFilteredEmployeesLegacy(
            List<Employee> employees,
            File outputFile,
            String departmentFilter,
            double minSalary) throws IOException {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            writer.write("ID,Name,Department,Salary");
            writer.newLine();

            // Filter and write employees
            employees.stream()
                    .filter(e -> e.getDepartment().equalsIgnoreCase(departmentFilter))
                    .filter(e -> e.getSalary() >= minSalary)
                    .map(Employee::toString)
                    .forEach(line -> {
                        try {
                            writer.write(line);
                            writer.newLine();
                        } catch (IOException e) {
                            throw new UncheckedIOException(e);
                        }
                    });
        }
    }
}
