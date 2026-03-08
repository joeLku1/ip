package clowns.handlers;

import clowns.task.Deadline;
import clowns.task.Events;
import clowns.task.Task;
import clowns.task.Todo;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FileHandler {
    private static final Path filepath = Paths.get( "src", "main", "java", "clowns", "data", "ClownList.txt");
    private static final DateTimeFormatter STORAGE_DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern("dd MMM uuuu HHmm")
            .toFormatter(Locale.ENGLISH)
            .withResolverStyle(ResolverStyle.STRICT);

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
                inputStore.add(parseDeadlineFromStorage(line.substring(7)));
            } else if (line.startsWith("[E]")) {
                inputStore.add(parseEventFromStorage(line.substring(7)));
            }
        }
        return inputStore;
    }

    /**
     * Parses a stored deadline string and converts it into a Deadline object
     * @param raw raw string containing the deadline
     * @return a Deadline object with parsed information
     */
    private Deadline parseDeadlineFromStorage(String raw) {
        int markerIndex = raw.lastIndexOf("(by:");
        if (markerIndex == -1) {
            throw new IllegalArgumentException("Invalid stored deadline format");
        }
        String description = raw.substring(0, markerIndex).trim();
        String bySegment = raw.substring(markerIndex + 4).trim();
        if (bySegment.endsWith(")")) {
            bySegment = bySegment.substring(0, bySegment.length() - 1).trim();
        }
        LocalDateTime by = parseDateTime(bySegment);
        return new Deadline(description, by);
    }

    /**
     * Parses a stored event string and converts it into an Events object
     * @param raw raw string containing the event
     * @return an Events object with parsed information
     */
    private Events parseEventFromStorage(String raw) {
        int fromIndex = raw.lastIndexOf("(from:");
        int toIndex = raw.lastIndexOf(" to:");
        if (fromIndex == -1 || toIndex == -1 || toIndex <= fromIndex) {
            throw new IllegalArgumentException("Invalid stored event format");
        }
        String description = raw.substring(0, fromIndex).trim();
        String fromSegment = raw.substring(fromIndex + 6, toIndex).trim();
        String toSegment = raw.substring(toIndex + 4).trim();
        if (toSegment.endsWith(")")) {
            toSegment = toSegment.substring(0, toSegment.length() - 1).trim();
        }
        LocalDateTime from = parseDateTime(fromSegment);
        LocalDateTime to = parseDateTime(toSegment);
        return new Events(description, from, to);
    }

    /**
     * Parses a date-time string from storage and converts it into a LocalDateTime object, handling any parsing exceptions
     * @param value
     * @return
     */
    private LocalDateTime parseDateTime(String value) {
        try {
            return LocalDateTime.parse(value, STORAGE_DATE_TIME_FORMATTER);
        } catch (DateTimeParseException ignored) {
            throw new IllegalArgumentException("Invalid date-time in storage: " + value);
        }
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
