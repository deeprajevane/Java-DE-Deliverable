package main.java.com.practice.exception;

import java.io.*;
import java.util.Properties;
import java.util.logging.Logger;

public class FileProcessor {
    private static final Logger logger = Logger.getLogger(FileProcessor.class.getName());
    public static void main(String[] args) {

        String filePath ="";
        try(InputStream input = new FileInputStream("src/main/resources/application.properties")){

            Properties pr = new Properties();
            if(input == null){
                logger.severe("File path is not correct");
                return;
            }
            pr.load(input);
            filePath = (pr.getProperty("file_path"));
         }
        catch (IOException e){
            e.printStackTrace();
        }

        String line;


        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))){


            while ((line = reader.readLine()) != null) {
                processLine(line);
            }
        } catch (FileNotFoundException e) {
            logger.severe("Error: File not found.");
        } catch (IOException e) {
            logger.severe("Error reading the file.");
        } catch (ParsingException e) {
            logger.severe("Parsing error: " + e.getMessage());
        }
    }

    static void processLine(String line) throws ParsingException {
        if (!line.matches("\\w+:\\d+")) {
            throw new ParsingException("Invalid line format: " + line);
        } else {
            String[] parts = line.split(":");
            logger.info("Name: " + parts[0] + ", Value: " + parts[1]);
        }
    }
}
