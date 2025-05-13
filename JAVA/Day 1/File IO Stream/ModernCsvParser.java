import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModernCsvParser {
    public static List<Employee> parseEmployees(Path inputPath) throws IOException {
        try (Stream<String> lines = Files.lines(inputPath)) {
            return lines
                    .skip(1) // Skip header
                    .map(Employee::parse)
                    .collect(Collectors.toList());
        }
    }

    public static void writeFilteredEmployeesModern(
            List<Employee> employees,
            Path outputPath,
            String departmentFilter,
            double minSalary) throws IOException {


        String header = "ID,Name,Department,Salary\n";


        Stream<String> dataLines = employees.stream()
                .filter(e -> e.getDepartment().equalsIgnoreCase(departmentFilter))
                .filter(e -> e.getSalary() >= minSalary)
                .map(Employee::toString);


        Files.write(outputPath, (Iterable<String>) Stream.concat(
                Stream.of(header),
                dataLines
        )::iterator);
    }


}
