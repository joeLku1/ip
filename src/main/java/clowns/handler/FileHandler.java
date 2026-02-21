package clowns.handler;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileHandler {
    private static final Path filepath = Paths.get("ip", "src", "main", "java", "clowns", "data", "ClownList.txt");

    /**
     * Creates a new file at the specified filepath
     */
    public void createFile() {
        try {
            java.io.File file = filepath.toFile();
            if (!file.exists()) {
                file.getParentFile().mkdirs(); // Create parent directories if they don't exist
                file.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Clowning occured during file creation: " + e.getMessage());
        }
    }
   
    /**
     * Reads data from file and returns it as a string
     * @return the data read from file as a string
     */
    public String readFromFile() {
        java.io.File file = filepath.toFile();
        StringBuilder data = new StringBuilder();
        if (!file.exists()) {
            return "";
        }
        try (java.util.Scanner scanner = new java.util.Scanner(filepath)) {
            while (scanner.hasNextLine()) {
                data.append(scanner.nextLine()).append("\n");
            }
        } catch (IOException e) {
            System.out.println("Clowning occured during file reading: " + e.getMessage());
        }
        return data.toString();
    }  

    /**
     * Writes contents of input string to file
     * New data will overwrite existing data in file
     * @param data the data to be written to file
     */
    public void writeToFile(String data) {
        try (java.io.FileWriter writer = new java.io.FileWriter(filepath.toFile())) {
            writer.write(data);
            writer.flush();
        } catch (IOException e) {
            System.out.println("Clowning occured during file writing: " + e.getMessage());
        }
    }
}
