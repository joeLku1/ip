package clowns;

import clowns.handlers.CommandHandler;
import clowns.handlers.FileHandler;
import clowns.task.Deadline;
import clowns.task.Events;
import clowns.task.TaskList;
import clowns.task.Todo;
import java.util.Scanner;

public class Ui {
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

    private final Scanner scanner;
    private final CommandHandler commandHandler;
    private final TaskList taskList;
    private final FileHandler storage;
    private boolean exit;

    /**
     * Constructor for Ui class
     * @param scanner
     * @param commandHandler
     * @param taskList
     * @param storage
     */
    public Ui(Scanner scanner, CommandHandler commandHandler, TaskList taskList, FileHandler storage) {
        this.scanner = scanner;
        this.commandHandler = commandHandler;
        this.taskList = taskList;
        this.storage = storage;
        this.exit = false;
    }

    /**
     * Displays the welcome message and prompts the user for their name
     */
    public void showWelcome() {
        System.out.println(LOGO + "\n  Hello, I am clowning!\n" + LINE_N);
        System.out.print("  Enter your name:\n");
        String name = scanner.nextLine();
        System.out.println("\n  Welcome, " + name + "! Let's clown together! Enter your command below:\n");
    }

    /**
     * Displays the goodbye message when the user exits the program
     */
    public void showGoodbye() {
        printString("  Clowning complete.\n  Goodbye fellow clown!");
    }

    /**
     * Checks if the user has entered the exit command
     * @return true if the user has entered the exit command, false otherwise
     */
    public boolean isExit() {
        return exit;
    }

    /**
     * Processes the next user input and executes the corresponding command
     * If the command is invalid, displays error message
     * If the command is valid, execute action and update the task list
     */
    public void processNextInput() {
        if (!scanner.hasNextLine()) {
            exit = true;
            return;
        }
        String userInput = scanner.nextLine();

        try {
            int command = commandHandler.parseCommand(userInput);

            switch (command) {
            case CommandHandler.EXIT:
                exit = true;
                break;
            case CommandHandler.LIST:
                String listOutput = "  Here is your list of clownery:\n";
                for (int i = 0; i < taskList.size(); i++) {
                    listOutput = listOutput.concat("  " + (i + 1) + ". " + taskList.get(i).toString() + "\n");
                }
                printString(listOutput);
                break;
            case CommandHandler.MARK:
                int indexMark = Integer.parseInt(commandHandler.getArgument()) - 1;
                if (taskList.isValidIndex(indexMark)) {
                    taskList.mark(indexMark);
                    printString("  Amazing work! Marked " + (indexMark + 1) + " as done.");
                    storage.writeToFile(taskList.asList());
                } else {
                    printString("  Task number " + (indexMark + 1) + " does not exist. You have " + taskList.size() + " tasks.");
                }
                break;
            case CommandHandler.UNMARK:
                int indexUnmark = Integer.parseInt(commandHandler.getArgument()) - 1;
                if (taskList.isValidIndex(indexUnmark)) {
                    taskList.unmark(indexUnmark);
                    printString("  What a clown. Task " + (indexUnmark + 1)
                            + " is now unmarked.\n  " + taskList.get(indexUnmark).toString());
                    storage.writeToFile(taskList.asList());
                } else {
                    printString("  Task number " + (indexUnmark + 1) + " does not exist. You have " + taskList.size() + " tasks.");
                }
                break;
            case CommandHandler.TODO:
                taskList.add(new Todo(commandHandler.getArgument()));
                printString("  ToDo added: " + userInput + "\n  You now have " + taskList.size() + " clownery in total.");
                storage.writeToFile(taskList.asList());
                break;
            case CommandHandler.DEADLINE:
                taskList.add(new Deadline(commandHandler.getArgument(), commandHandler.getDeadlineBy()));
                printString("  Deadline added: " + userInput + "\n  You now have " + taskList.size() + " clownery in total.");
                storage.writeToFile(taskList.asList());
                break;
            case CommandHandler.EVENT:
                taskList.add(new Events(commandHandler.getArgument(),
                        commandHandler.getEventFrom(), commandHandler.getEventTo()));
                printString("  Event added: " + userInput + "\n  You now have " + taskList.size() + " clownery in total.");
                storage.writeToFile(taskList.asList());
                break;
            case CommandHandler.DELETE:
                int indexDelete = Integer.parseInt(commandHandler.getArgument()) - 1;
                if (taskList.isValidIndex(indexDelete)) {
                    String deletedTask = taskList.delete(indexDelete).toString();
                    printString("  Deleted task: " + deletedTask + "\n  You now have " + taskList.size() + " clownery in total.");
                    storage.writeToFile(taskList.asList());
                } else {
                    printString("  Task number " + (indexDelete + 1) + " does not exist. You have " + taskList.size() + " tasks.");
                }
                break;
            case CommandHandler.FIND:
                String keyword = commandHandler.getArgument().toLowerCase();
                String findOutput = "  Here are the matching clownery tasks:\n";
                int matchCount = 0;
                for (int i = 0; i < taskList.size(); i++) {
                    if (taskList.get(i).getDescription().toLowerCase().contains(keyword)) {
                        matchCount++;
                        findOutput = findOutput.concat("  " + matchCount + ". " + taskList.get(i).toString() + "\n");
                    }
                }
                if (matchCount == 0) {
                    printString("  No matching clownery found for: " + commandHandler.getArgument());
                } else {
                    printString(findOutput);
                }
                break;
            default:
                break;
            }
        } catch (ClownsException e) {
            printString("  Error: " + e.getMessage());
        }
    }

    public void closeScanner() {
        scanner.close();
    }

    private void printString(String output) {
        System.out.println(LINE);
        System.out.println(output);
        System.out.println(LINE_N);
    }
}