package clowns.handlers;

import clowns.task.Deadline;
import clowns.task.Events;
import clowns.task.Task;
import clowns.task.Todo;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private static final Path filepath = Paths.get( "src", "main", "java", "clowns", "data", "ClownList.txt");

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
     * Writes the current list of tasks to file
     * @param inputStore the list of tasks to be written to file
     */
    public void writeToFile(List<Task> inputStore) {
        StringBuilder data = new StringBuilder();
        for (Task task : inputStore) {
            data.append(task.toString()).append("\n");
        }
        try (java.io.FileWriter writer = new java.io.FileWriter(filepath.toFile())) {
            writer.write(data.toString());
            writer.flush();
        } catch (IOException e) {
            System.out.println("Clowning occured during file writing: " + e.getMessage());
        }
    }

    /**
     * Loads tasks from file and adds them to the input store
     * @return the list of tasks loaded from file
     */
    public List<Task> loadFromFile() {
        List<Task> inputStore = new ArrayList<>();
        String data = readFromFile();
        if (data.isEmpty()) {
            return inputStore;
        }
        String[] lines = data.split("\n");
        for (String line : lines) {
            if (line.startsWith("[T]")) {
                inputStore.add(new Todo(line.substring(7)));
            } else if (line.startsWith("[D]")) {
                inputStore.add(new Deadline(line.substring(7)));
            } else if (line.startsWith("[E]")) {
                inputStore.add(new Events(line.substring(7)));
            }
        }
        return inputStore;
    }
   
    /**
     * Reads data from file and returns it as a string
     * @return the data read from file as a string
     */
    private String readFromFile() {
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
}
