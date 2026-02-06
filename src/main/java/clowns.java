import java.util.Scanner;

public class Clowns {
    /**
     * Print input string with lines before and after
     * @param s
     */
    public static void printString(String s) {
        System.out.println("  ---------------------------------");
        System.out.println(s);
        System.out.println("  ---------------------------------\n");
    }

    public static void main(String[] args) {
        String logo =
            """
            $$$$$$\\  $$\\       $$$$$$\\  $$\\      $$\\ $$\\   $$\\ 
            $$  __$$\\ $$ |     $$  __$$\\ $$ | $\\  $$ |$$$\\  $$ | 
            $$ /  \\__|$$ |     $$ /  $$ |$$ |$$$\\ $$ |$$$$\\ $$ | 
            $$ |      $$ |     $$ |  $$ |$$ $$ $$\\$$ |$$ $$\\$$ | 
            $$ |      $$ |     $$ |  $$ |$$$$  _$$$$ |$$ \\$$$$ | 
            $$ |  $$\\ $$ |     $$ |  $$ |$$$  / \\$$$ |$$ |\\$$$ | 
            \\$$$$$$  |$$$$$$$$\\ $$$$$$  |$$  /   \\$$ |$$ | \\$$ | 
             \\______/ \\________|\\______/ \\__/     \\__|\\__|  \\__| 
        """;

        System.out.println(logo);
        System.out.println("  Hello, I am clowning!");
        System.out.println("  ---------------------------------\n");

        Scanner scanner = new Scanner(System.in);
        System.out.print("  Enter your name:\n"); 
        String name = scanner.nextLine();
        System.out.println("\nWelcome, " + name + "! Let's clown together! Enter your command below:\n");

        boolean exitFlag = true;
        Task[] inputStore = new Task[100];
        int count = 0;

        while (exitFlag) { 
            String userInput = scanner.nextLine();
            if (userInput.equalsIgnoreCase("exit")) {
                exitFlag = false;
            }
            else if (userInput.equalsIgnoreCase("list")) {
                String listOutput = "  Here is your list of clownery:\n";
                for (int i = 0; i < count; i++) {
                    listOutput = listOutput.concat("  " + (i + 1) + ". " + inputStore[i].toString() + "\n");
                }
                printString(listOutput);
            }
            else if (userInput.length() >= 4 && userInput.substring(0, 4).equalsIgnoreCase("mark")) {
                int indexMark = Integer.parseInt(userInput.substring(5)) - 1;
                if (indexMark >= 0 && indexMark < count) {
                    inputStore[indexMark].markAsDone();
                    printString("  Amazing work! Marked " + (indexMark + 1) + " as done.");
                } else {
                    printString("  Invalid task number to mark.");
                }
            }
            else if (userInput.length() >= 6 && userInput.substring(0, 6).equalsIgnoreCase("unmark")) {
                int indexUnmark = Integer.parseInt(userInput.substring(7)) - 1;
                if (indexUnmark >= 0 && indexUnmark < count) {
                    inputStore[indexUnmark].markAsUndone();
                    printString("  What a clown. Task " + (indexUnmark + 1) + " is now unmarked.\n  " + inputStore[indexUnmark].toString());
                } else {
                    printString("  Invalid task number to unmark.");
                }
            }
            else if (userInput.length() >= 5 && userInput.substring(0, 5).equalsIgnoreCase("todo ")) {
                inputStore[count] = new Todo(userInput.substring(5));
                printString("  added: " + userInput);
                count++;
            }
            else if (userInput.length() >= 7 && userInput.substring(0, 6).equalsIgnoreCase("event ")) {
                inputStore[count] = new Events(userInput.substring(6));
                printString("  added: " + userInput);
                count++;
            }
            else if (userInput.length() >= 10 && userInput.substring(0, 9).equalsIgnoreCase("deadline ")) {
                inputStore[count] = new Deadline(userInput.substring(9));
                printString("  added: " + userInput);
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
