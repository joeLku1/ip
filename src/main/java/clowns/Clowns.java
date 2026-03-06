package clowns;
import clowns.handlers.CommandHandler;
import clowns.task.Deadline;
import clowns.task.Events;
import clowns.task.TaskList;
import clowns.task.Todo;
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

    private static TaskList taskList;
    private static clowns.handlers.Storage storage;
    

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
        
        storage = new clowns.handlers.Storage();
        storage.createFile();
        taskList = new TaskList(storage.loadFromFile());

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
                        printString("  What a clown. Task " + (indexUnmark + 1) + " is now unmarked.\n  " + taskList.get(indexUnmark).toString());
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
                    taskList.add(new Deadline(commandHandler.getArgument()));
                    printString("  Deadline added: " + userInput + "\n  You now have " + taskList.size() + " clownery in total.");
                    storage.writeToFile(taskList.asList());
                    break;
                case CommandHandler.EVENT:
                    taskList.add(new Events(commandHandler.getArgument()));
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
