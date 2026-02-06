import java.util.Scanner;

public class Clowns {
    /**
     * Print input string with lines before and after
     * @param s
     */

    public static final String LOGO =
            """
            $$$$$$\\   $$\\      $$$$$$\\   $$\\      $$\\ $$\\   $$\\ 
            $$  __$$\\ $$ |     $$  __$$\\ $$ | $\\  $$ |$$$\\  $$ | 
            $$ /  \\__|$$ |     $$ /  $$ |$$ |$$$\\ $$ |$$$$\\ $$ | 
            $$ |      $$ |     $$ |  $$ |$$ $$ $$\\$$ |$$ $$\\$$ | 
            $$ |      $$ |     $$ |  $$ |$$$$  _$$$$ |$$ \\$$$$ | 
            $$ |  $$\\ $$ |     $$ |  $$ |$$$  / \\$$$ |$$ |\\$$$ | 
            \\$$$$$$  |$$$$$$$$\\ $$$$$$  |$$  /   \\$$ |$$ | \\$$ | 
             \\______/ \\________|\\______/ \\__/     \\__|\\__|  \\__| 
            """;
    public static final String LINE = "  ---------------------------------";
    public static final String LINE_N = "  ---------------------------------\n";
    public static final int MARK_LEN = 4;
    public static final int UNMARK_LEN = 6;
    public static final int TODO_LEN = 5;
    public static final int EVENT_LEN = 6;
    public static final int DEADLINE_LEN = 9;


    public static void printString(String s) {
        System.out.println(LINE);
        System.out.println(s);
        System.out.println(LINE_N);
    }

    public static void main(String[] args) {
        System.out.println(LOGO);
        System.out.println("  Hello, I am clowning!");
        System.out.println(LINE_N);

        Scanner scanner = new Scanner(System.in);
        System.out.print("  Enter your name:\n"); 
        String name = scanner.nextLine();
        System.out.println("\n  Welcome, " + name + "! Let's clown together! Enter your command below:\n");

        boolean toContinue = true;
        Task[] inputStore = new Task[100];
        int count = 0;

        while (toContinue) {
            if (!scanner.hasNextLine()) {
                break;
            }
            String userInput = scanner.nextLine();
            if (userInput.equalsIgnoreCase("exit")) {
                toContinue = false;
            }
            else if (userInput.equalsIgnoreCase("list")) {
                String listOutput = "  Here is your list of clownery:\n";
                for (int i = 0; i < count; i++) {
                    listOutput = listOutput.concat("  " + (i + 1) + ". " + inputStore[i].toString() + "\n");
                }
                printString(listOutput);
            }
            else if (userInput.length() >= MARK_LEN && userInput.substring(0, MARK_LEN).equalsIgnoreCase("mark")) {
                int indexMark = Integer.parseInt(userInput.substring(5)) - 1;
                if (indexMark >= 0 && indexMark < count) {
                    inputStore[indexMark].markAsDone();
                    printString("  Amazing work! Marked " + (indexMark + 1) + " as done.");
                } else {
                    printString("  Invalid task number to mark.");
                }
            }
            else if (userInput.length() >= UNMARK_LEN && userInput.substring(0, UNMARK_LEN).equalsIgnoreCase("unmark")) {
                int indexUnmark = Integer.parseInt(userInput.substring(7)) - 1;
                if (indexUnmark >= 0 && indexUnmark < count) {
                    inputStore[indexUnmark].markAsUndone();
                    printString("  What a clown. Task " + (indexUnmark + 1) + " is now unmarked.\n  " + inputStore[indexUnmark].toString());
                } else {
                    printString("  Invalid task number to unmark.");
                }
            }
            else if (userInput.length() >= TODO_LEN && userInput.substring(0, TODO_LEN).equalsIgnoreCase("todo ")) {
                inputStore[count] = new Todo(userInput.substring(5));
                printString("  added: " + userInput + "\n  You now have " + (count + 1) + " clownery in total.");
                count++;
            }
            else if (userInput.length() >= EVENT_LEN && userInput.substring(0, EVENT_LEN).equalsIgnoreCase("event ")) {
                inputStore[count] = new Events(userInput.substring(6));
                printString("  added: " + userInput + "\n  You now have " + (count + 1) + " clownery in total.");
                count++;
            }
            else if (userInput.length() >= DEADLINE_LEN && userInput.substring(0, DEADLINE_LEN).equalsIgnoreCase("deadline ")) {
                inputStore[count] = new Deadline(userInput.substring(9));
                printString("  added: " + userInput + "\n  You now have " + (count + 1) + " clownery in total.");
                count++;
            }
            else {
                printString(userInput);
            }
        }
        
        printString("  Clowning complete.\n  Goodbye fellow clown!");
        scanner.close();
    }
}
