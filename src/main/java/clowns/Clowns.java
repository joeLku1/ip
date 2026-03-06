package clowns;
import clowns.handlers.CommandHandler;
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

    private static List<Task> inputStore;
    private static clowns.handlers.FileHandler fileHandler;

    /**
     * Print input string with lines before and after
     * @param s
    */
    public static void printString(String s) {
        System.out.println(LINE);
        System.out.println(s);
        System.out.println(LINE_N);
    }

    public static void main(String[] args) {        
        System.out.println(LOGO + "\n  Hello, I am clowning!\n" + LINE_N);
        
        Scanner scanner = new Scanner(System.in);
        System.out.print("  Enter your name:\n"); 
        String name = scanner.nextLine();
        System.out.println("\n  Welcome, " + name + "! Let's clown together! Enter your command below:\n");
        
        boolean toContinue = true;
        int count = 0;
        List<Task> inputStore = new ArrayList<>();
        
        fileHandler = new clowns.handlers.FileHandler();
        fileHandler.createFile();
        fileHandler.loadFromFile();

        CommandHandler commandHandler = new CommandHandler();

        while (toContinue) {
            if (!scanner.hasNextLine()) {
                break;
            }
            String userInput = scanner.nextLine();
            
            try {
                int command = commandHandler.parseCommand(userInput);

                switch (command) {
                case CommandHandler.EXIT:
                    toContinue = false;
                    break;
                case CommandHandler.LIST:
                    String listOutput = "  Here is your list of clownery:\n";
                    for (int i = 0; i < count; i++) {
                        listOutput = listOutput.concat("  " + (i + 1) + ". " + inputStore.get(i).toString() + "\n");
                    }
                    printString(listOutput);
                    break;
                case CommandHandler.MARK:
                    int indexMark = Integer.parseInt(commandHandler.getArgument()) - 1;
                    if (indexMark >= 0 && indexMark < count) {
                        inputStore.get(indexMark).markAsDone();
                        printString("  Amazing work! Marked " + (indexMark + 1) + " as done.");
                        fileHandler.writeToFile(inputStore);
                    } else {
                        printString("  Task number " + (indexMark + 1) + " does not exist. You have " + count + " tasks.");
                    }
                    break;
                case CommandHandler.UNMARK:
                    int indexUnmark = Integer.parseInt(commandHandler.getArgument()) - 1;
                    if (indexUnmark >= 0 && indexUnmark < count) {
                        inputStore.get(indexUnmark).markAsUndone();
                        printString("  What a clown. Task " + (indexUnmark + 1) + " is now unmarked.\n  " + inputStore.get(indexUnmark).toString());
                        fileHandler.writeToFile(inputStore);
                    } else {
                        printString("  Task number " + (indexUnmark + 1) + " does not exist. You have " + count + " tasks.");
                    }
                    break;
                case CommandHandler.TODO:
                    inputStore.add(new Todo(commandHandler.getArgument()));
                    printString("  ToDo added: " + userInput + "\n  You now have " + inputStore.size() + " clownery in total.");
                    count++;
                    fileHandler.writeToFile(inputStore);
                    break;
                case CommandHandler.DEADLINE:
                    inputStore.add(new Deadline(commandHandler.getArgument()));
                    printString("  Deadline added: " + userInput + "\n  You now have " + inputStore.size() + " clownery in total.");
                    count++;
                    fileHandler.writeToFile(inputStore);
                    break;
                case CommandHandler.EVENT:
                    inputStore.add(new Events(commandHandler.getArgument()));
                    printString("  Event added: " + userInput + "\n  You now have " + inputStore.size() + " clownery in total.");
                    count++;
                    fileHandler.writeToFile(inputStore);
                    break;
                case CommandHandler.DELETE:
                    int indexDelete = Integer.parseInt(commandHandler.getArgument()) - 1;
                    if (indexDelete >= 0 && indexDelete < count) {
                        String deletedTask = inputStore.get(indexDelete).toString();
                        inputStore.remove(indexDelete);
                        count--;
                        printString("  Deleted task: " + deletedTask + "\n  You now have " + count + " clownery in total.");
                        fileHandler.writeToFile(inputStore);
                    } else {
                        printString("  Task number " + (indexDelete + 1) + " does not exist. You have " + count + " tasks.");
                    }
                    break;
                default:
                    break;
                }
            } catch (ClownsException e) {
                printString("  Error: " + e.getMessage());
            }
        }
        printString("  Clowning complete.\n  Goodbye fellow clown!");
        scanner.close();
    }
}
