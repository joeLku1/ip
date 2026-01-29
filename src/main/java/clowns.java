import java.util.Scanner;

public class clowns {
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
            String usr_input = scanner.nextLine();
            if (usr_input.equalsIgnoreCase("exit")) {
                exitFlag = false;
            }
            else if (usr_input.equalsIgnoreCase("list")) {
                String listOutput = "  Here is your list of clownery:\n";
                for (int i = 0; i < count; i++) {
                    listOutput = listOutput.concat("  " + (i + 1) + ". " + inputStore[i].toString() + "\n");
                }
                printString(listOutput);
            }
            else if (usr_input.length() >= 4 && usr_input.substring(0, 4).equalsIgnoreCase("mark")) {
                int indexMark = Integer.parseInt(usr_input.substring(5)) - 1;
                if (indexMark >= 0 && indexMark < count) {
                    inputStore[indexMark].markAsDone();
                    printString("  Amazing work! Marked " + (indexMark + 1) + " as done.");
                } else {
                    printString("  Invalid task number to mark.");
                }
            }
            else if (usr_input.length() >= 6 && usr_input.substring(0, 6).equalsIgnoreCase("unmark")) {
                int indexUnmark = Integer.parseInt(usr_input.substring(7)) - 1;
                if (indexUnmark >= 0 && indexUnmark < count) {
                    inputStore[indexUnmark].markAsUndone();
                    printString("  What a clown. Task " + (indexUnmark + 1) + " is now unmarked.\n  " + inputStore[indexUnmark].toString());
                } else {
                    printString("  Invalid task number to unmark.");
                }
            }
            else {
                inputStore[count] = new Task(usr_input);
                printString("  added: " + usr_input);
                count++;
            }
        }
        
        printString("  Clowning complete.\n  Goodbye fellow clown!");

        scanner.close();
    }
}
