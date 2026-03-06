package clowns.handler;

import clowns.ClownsException;

public class CommandHandler {
    public static final int EXIT = 0;
    public static final int LIST = 1;
    public static final int MARK = 2;
    public static final int UNMARK = 3;
    public static final int TODO = 4;
    public static final int DEADLINE = 5;
    public static final int EVENT = 6;
    public static final int DELETE = 7;
    public static final int INVALID = -1;

    private String currentInput;
    private String argument;

    /**
     * Takes in user input and returns an integer representing the command type
     * @param input the user input string
     * @return an integer representing the command type
     * @throws ClownsException if the command is invalid or has invalid arguments
     */
    public int parseCommand(String input) throws ClownsException {
        this.currentInput = input.trim();
        this.argument = "";
        
        if (currentInput.isEmpty()) {
            throw new ClownsException("Empty command entered. Please enter a valid command.");
        }
        
        if (currentInput.equalsIgnoreCase("exit")) {
            return EXIT;
        } else if (currentInput.equalsIgnoreCase("list")) {
            return LIST;
        } else if (currentInput.length() >= 4 && currentInput.substring(0, 4).equalsIgnoreCase("mark")) {
            return validateMarkUnmark(currentInput, "mark", 5, MARK);
        } else if (currentInput.length() >= 6 && currentInput.substring(0, 6).equalsIgnoreCase("unmark")) {
            return validateMarkUnmark(currentInput, "unmark", 7, UNMARK);
        } else if (currentInput.length() >= 4 && currentInput.substring(0, 4).equalsIgnoreCase("todo")) {
            return validateTodo(currentInput);
        } else if (currentInput.length() >= 8 && currentInput.substring(0, 8).equalsIgnoreCase("deadline")) {
            return validateDeadline(currentInput);
        } else if (currentInput.length() >= 5 && currentInput.substring(0, 5).equalsIgnoreCase("event")) {
            return validateEvent(currentInput);
        } else if (currentInput.length() >= 6 && currentInput.substring(0, 6).equalsIgnoreCase("delete")) {
            return validateDelete(currentInput);
        } else {
            throw new ClownsException("Unknown command: '" + currentInput.split(" ")[0] + "'.\n  Valid commands: todo, deadline, event, mark, unmark, delete, list, exit");
        }
    }

    private int validateMarkUnmark(String input, String command, int startIndex, int returnType) throws ClownsException {
        if (input.length() <= startIndex || input.substring(startIndex).trim().isEmpty()) {
            throw new ClownsException("Missing task number for '" + command + "'.\n  Usage: " + command + " <task number>");
        }
        String numStr = input.substring(startIndex).trim();
        try {
            int num = Integer.parseInt(numStr);
            if (num <= 0) {
                throw new ClownsException("Task number must be positive. You entered: " + num);
            }
            this.argument = numStr;
        } catch (NumberFormatException e) {
            throw new ClownsException("Invalid task number: '" + numStr + "'. Please enter a valid number.");
        }
        return returnType;
    }

    private int validateTodo(String input) throws ClownsException {
        if (input.length() <= 5 || input.substring(5).trim().isEmpty()) {
            throw new ClownsException("Todo description cannot be empty.\n  Usage: todo <description>");
        }
        this.argument = input.substring(5).trim();
        return TODO;
    }

    private int validateDeadline(String input) throws ClownsException {
        if (input.length() <= 9 || input.substring(9).trim().isEmpty()) {
            throw new ClownsException("Deadline description cannot be empty.\n  Usage: deadline <description> /by <date>");
        }
        String desc = input.substring(9).trim();
        if (!desc.contains("/by")) {
            throw new ClownsException("Deadline must include '/by' followed by a date.\n  Usage: deadline <description> /by <date>");
        }
        String[] parts = desc.split("/by", 2);
        if (parts[0].trim().isEmpty()) {
            throw new ClownsException("Deadline description before '/by' cannot be empty.");
        }
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new ClownsException("Deadline date after '/by' cannot be empty.");
        }
        this.argument = desc;
        return DEADLINE;
    }

    private int validateEvent(String input) throws ClownsException {
        if (input.length() <= 6 || input.substring(6).trim().isEmpty()) {
            throw new ClownsException("Event description cannot be empty.\n  Usage: event <description> /from <start> /to <end>");
        }
        String desc = input.substring(6).trim();
        if (!desc.contains("/from")) {
            throw new ClownsException("Event must include '/from' followed by a start time.\n  Usage: event <description> /from <start> /to <end>");
        }
        if (!desc.contains("/to")) {
            throw new ClownsException("Event must include '/to' followed by an end time.\n  Usage: event <description> /from <start> /to <end>");
        }
        String[] fromParts = desc.split("/from", 2);
        if (fromParts[0].trim().isEmpty()) {
            throw new ClownsException("Event description before '/from' cannot be empty.");
        }
        String[] toParts = fromParts[1].split("/to", 2);
        if (toParts[0].trim().isEmpty()) {
            throw new ClownsException("Event start time after '/from' cannot be empty.");
        }
        if (toParts.length < 2 || toParts[1].trim().isEmpty()) {
            throw new ClownsException("Event end time after '/to' cannot be empty.");
        }
        this.argument = desc;
        return EVENT;
    }

    private int validateDelete(String input) throws ClownsException {
        if (input.length() <= 7 || input.substring(7).trim().isEmpty()) {
            throw new ClownsException("Missing task number for 'delete'.\n  Usage: delete <task number>");
        }
        String numStr = input.substring(7).trim();
        try {
            int num = Integer.parseInt(numStr);
            if (num <= 0) {
                throw new ClownsException("Task number must be positive. You entered: " + num);
            }
            this.argument = numStr;
        } catch (NumberFormatException e) {
            throw new ClownsException("Invalid task number: '" + numStr + "'. Please enter a valid number.");
        }
        return DELETE;
    }

    /**
     * Returns the current input string
     * @return the current input string
     */
    public String getCurrentInput() {
        return currentInput;
    }

    /**
     * Returns the parsed argument from the command
     * @return the argument string
     */
    public String getArgument() {
        return argument;
    }
}
