package clowns;
import clowns.task.Deadline;
import clowns.task.Events;
import clowns.task.Task;
import clowns.task.Todo;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Clowns {
    public static final String LOGO =
        """
        $$$$$$\\   $$\\       /$$$$\\   $$\\      $$\\ $$\\   $$\\ 
        $$  __$$\\ $$ |     $$  __$$\\ $$ | $\\  $$ |$$$\\  $$ | 
        $$ /  \\__|$$ |     $$ /  $$ |$$ |$$$\\ $$ |$$$$\\ $$ | 
        $$ |      $$ |     $$ |  $$ |$$ $$ $$\\$$ |$$ $$\\$$ | 
        $$ |      $$ |     $$ |  $$ |$$$$  _$$$$ |$$ \\$$$$ | 
        $$ |  $$\\ $$ |     $$ |  $$ |$$$  / \\$$$ |$$ |\\$$$ | 
        \\$$$$$$  |$$$$$$$$\\ $$$$$$  |$$  /   \\$$ |$$ | \\$$ | 
        \\______/ \\________|\\______/ \\___/     \\__|\\__|  \\__| 
        """;
    private static final String LINE = "  ---------------------------------";
    private static final String LINE_N = "  ---------------------------------\n";
    private static final int MARK_LEN = 4;
    private static final int UNMARK_LEN = 6;
    private static final int TODO_LEN = 4;
    private static final int EVENT_LEN = 5;
    private static final int DEADLINE_LEN = 8;
    private static final int DELETE_LEN = 6;

    private static List<Task> inputStore;
    private static clowns.handler.FileHandler fileHandler;

    /**
     * Print input string with lines before and after
     * @param s
    */
    public static void printString(String s) {
        System.out.println(LINE);
        System.out.println(s);
        System.out.println(LINE_N);
    }

    public static void writeToFile() {
        StringBuilder data = new StringBuilder();
        for (Task task : inputStore) {
            data.append(task.toString()).append("\n");
        }
        fileHandler.writeToFile(data.toString());
    }

    public static void loadFromFile() {
        String data = fileHandler.readFromFile();
        if (data.isEmpty()) {
            return;
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
    }

    public static void main(String[] args) {        
        System.out.println(LOGO + "\n  Hello, I am clowning!\n" + LINE_N);
        
        Scanner scanner = new Scanner(System.in);
        System.out.print("  Enter your name:\n"); 
        String name = scanner.nextLine();
        System.out.println("\n  Welcome, " + name + "! Let's clown together! Enter your command below:\n");
        
        boolean toContinue = true;
        // Task[] inputStore = new Task[100];
        inputStore = new ArrayList<>();
        int count = 0;
        
        fileHandler = new clowns.handler.FileHandler();
        fileHandler.createFile();
        loadFromFile();

        while (toContinue) {
            if (!scanner.hasNextLine()) {
                break;
            }
            String userInput = scanner.nextLine();
            if (userInput.equalsIgnoreCase("exit")) {
                toContinue = false;
            } else if (userInput.equalsIgnoreCase("list")) {
                String listOutput = "  Here is your list of clownery:\n";
                for (int i = 0; i < count; i++) {
                    // listOutput = listOutput.concat("  " + (i + 1) + ". " + inputStore[i].toString() + "\n");
                    listOutput = listOutput.concat("  " + (i + 1) + ". " + inputStore.get(i).toString() + "\n");
                }
                printString(listOutput);
            } else if (userInput.length() >= MARK_LEN && userInput.substring(0, MARK_LEN).equalsIgnoreCase("mark")) {
                int indexMark = Integer.parseInt(userInput.substring(5)) - 1;
                if (indexMark >= 0 && indexMark < count) {
                    // inputStore[indexMark].markAsDone();
                    inputStore.get(indexMark).markAsDone();
                    printString("  Amazing work! Marked " + (indexMark + 1) + " as done.");
                    writeToFile();
                } else {
                    printString("  Invalid task number to mark.");
                }
            } else if (userInput.length() >= UNMARK_LEN && userInput.substring(0, UNMARK_LEN).equalsIgnoreCase("unmark")) {
                int indexUnmark = Integer.parseInt(userInput.substring(7)) - 1;
                if (indexUnmark >= 0 && indexUnmark < count) {
                    // inputStore[indexUnmark].markAsUndone();
                    // printString("  What a clown. Task " + (indexUnmark + 1) + " is now unmarked.\n  " + inputStore[indexUnmark].toString());
                    inputStore.get(indexUnmark).markAsUndone();
                    printString("  What a clown. Task " + (indexUnmark + 1) + " is now unmarked.\n  " + inputStore.get(indexUnmark).toString());
                    writeToFile();
                } else {
                    printString("  Invalid task number to unmark.");
                }
            } else if (userInput.length() >= TODO_LEN && userInput.substring(0, TODO_LEN).equalsIgnoreCase("todo")) {
                try {
                    // inputStore[count] = new Todo(userInput.substring(5));
                    // printString("  todo added: " + userInput + "\n  You now have " + (count + 1) + " clownery in total.");
                    inputStore.add(new Todo(userInput.substring(5)));
                    printString("  todo added: " + userInput + "\n  You now have " + inputStore.size() + " clownery in total.");
                    count++;
                    writeToFile();
                } catch (StringIndexOutOfBoundsException e) {
                    printString("  Stop clowning, what are you todo-ing? >:(");
                }
            } else if (userInput.length() >= DEADLINE_LEN && userInput.substring(0, DEADLINE_LEN).equalsIgnoreCase("deadline")) {
                try {
                    // inputStore[count] = new Deadline(userInput.substring(9));
                    // printString("  deadline added: " + userInput + "\n  You now have " + (count + 1) + " clownery in total.");
                    inputStore.add(new Deadline(userInput.substring(9)));
                    printString("  deadline added: " + userInput + "\n  You now have " + inputStore.size() + " clownery in total.");
                    count++;
                    writeToFile();
                } catch (StringIndexOutOfBoundsException e) {
                    printString("  Stop clowning, an empty deadline?? >:(");
                }
            } else if (userInput.length() >= EVENT_LEN && userInput.substring(0, EVENT_LEN).equalsIgnoreCase("event")) {
                try {
                    // inputStore[count] = new Events(userInput.substring(6));
                    // printString("  event added: " + userInput + "\n  You now have " + (count + 1) + " clownery in total.");
                    inputStore.add(new Events(userInput.substring(6)));
                    printString("  event added: " + userInput + "\n  You now have " + inputStore.size() + " clownery in total.");
                    count++;
                    writeToFile();
                } catch (StringIndexOutOfBoundsException e) {
                    printString("  Stop clowning, what event is this? >:(");
                }
            } else if (userInput.length() >= DELETE_LEN && userInput.substring(0, DELETE_LEN).equalsIgnoreCase("delete")) {
                try {
                    int indexDelete = Integer.parseInt(userInput.substring(7)) - 1;
                    if (indexDelete >= 0 && indexDelete < count) {
                        // String deletedTask = inputStore[indexDelete].toString();
                        String deletedTask = inputStore.get(indexDelete).toString();
                        for (int i = indexDelete; i < count - 1; i++) {
                            // inputStore[i] = inputStore[i + 1];
                            inputStore.set(i, inputStore.get(i + 1));
                        }
                        // inputStore[count - 1] = null;
                        inputStore.remove(count - 1);
                        count--;
                        printString("  Deleted task: " + deletedTask + "\n  You now have " + count + " clownery in total.");
                        writeToFile();
                    } else {
                        printString("  Invalid task number to delete.");
                    }
                } catch (NumberFormatException e) {
                    printString("  Stop clowning, what task number do you want to delete? >:(");
                }
            } else {
                printString("  Invalid command, absolute clownery.");
            }
        }
        printString("  Clowning complete.\n  Goodbye fellow clown!");
        scanner.close();
    }
}
