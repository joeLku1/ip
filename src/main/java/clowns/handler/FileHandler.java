package clowns.handler;

import java.io.IOException;
import java.util.Scanner;
import java.io.FileWriter;

public class FileHandler {
    private static final String filepath = "../data/ClownList.txt";
    private boolean fileExists = false;
    private StringBuilder data;                 // StringBuilder to store data read from file
    private java.util.Scanner fileRead;         // Scanner to read from file
    private java.io.FileWriter fileWrite;       // FileWriter to write to file

    /**
     * Checks if the file exists
     * @return true if the file exists, false otherwise
     */
    public boolean getfileExists() {
        return fileExists;
    }

    /**
     * Creates a new file if it does not exist
     * @return true if the file was created successfully, false otherwise
     */
    private boolean createFile() {
        try {
            java.io.File file = new java.io.File(filepath);
            file.getParentFile().mkdirs(); // Create parent directories if they don't exist
            file.createNewFile();
            this.fileExists = true;
            return this.fileExists;
        } catch (IOException e) {
            System.out.println("Clowning occured during file creation: " + e.getMessage());
        }
        return this.fileExists;
    }
    
    /**
     * Initializes StringBuilder, FileWriter, Scanner for file handling
    */
    private void initializeFileHandling() {
        try {
            this.data = new StringBuilder();
            this.fileRead = new java.util.Scanner(filepath);
            this.fileWrite = new java.io.FileWriter(filepath);
        } catch (IOException e) {
            System.out.println("Clowning occured during handler initialization: " + e.getMessage());
        }
    }

    /**
     * Closes Scanner and FileWriter after program exit
     */
    public void closeFileHandling() {
        if (this.fileRead != null) {
            this.fileRead.close();
        }
        if (this.fileWrite != null) {
            try {
                this.fileWrite.close();
            } catch (IOException e) {
                System.out.println("Clowning occured during file handling closing: " + e.getMessage());
            }
        }
    }

    /**
     * Reads data from file and returns it as a string
     * @return the data read from file as a string
     */
    public String readFromFile() {
        java.io.File file = new java.io.File(filepath);
        if (!file.exists()) {
            return "";
        }
        while (this.fileRead.hasNextLine()) {
            this.data.append(this.fileRead.nextLine()).append("\n");
        }
        return this.data.toString();
    }  

    /**
     * Write to file, creates file if it does not already exist
     * @param data the data to be written to file
     */
    public boolean writeToFile(String data) {
        if (!this.fileExists) {
            createFile();
        }
        try {
            this.fileWrite = new java.io.FileWriter(filepath);
            this.fileWrite.write(data);
            return true;
        } catch (IOException e) {
            System.out.println("Clowning occured during file writing: " + e.getMessage());
        }
        return false;
    }
}
