import java.io.*;

class ParsingException extends Exception {
    public ParsingException(String message) {
        super(message);
    }
}

public class FileProcessor {
    public static void main(String[] args) {
        String filePath = "input.txt";
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                processLine(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found.");
        } catch (IOException e) {
            System.out.println("Error reading the file.");
        } catch (ParsingException e) {
            System.out.println("Parsing error: " + e.getMessage());
        } finally {
            try {
                if (reader != null) reader.close();
                System.out.println("File closed.");
            } catch (IOException e) {
                System.out.println("Error closing the file.");
            }
        }
    }

    static void processLine(String line) throws ParsingException {
        if (!line.matches("\\w+:\\d+")) {
            throw new ParsingException("Invalid line format: " + line);
        } else {
            String[] parts = line.split(":");
            System.out.println("Name: " + parts[0] + ", Value: " + parts[1]);
        }
    }
}
