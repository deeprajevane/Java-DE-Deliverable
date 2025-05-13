import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CsvParserDemo {
    public static void main(String[] args) {
        try {
            // Legacy I/O example
            File legacyInput = new File("employee.csv");
            List<Employee> employeesLegacy = CsvParser.parseEmployees(legacyInput);
            CsvParser.writeFilteredEmployeesLegacy(
                    employeesLegacy,
                    new File("filtered_employees_legacy.csv"),
                    "IT",
                    50000.0);

            // NIO.2 example
            Path modernInput = Paths.get("employee.csv");
            List<Employee> employeesModern = ModernCsvParser.parseEmployees(modernInput);
            ModernCsvParser.writeFilteredEmployeesModern(
                    employeesModern,
                    Paths.get("filtered_employees_modern.csv"),
                    "Finance",
                    60000.0);

            System.out.println("CSV processing completed successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}