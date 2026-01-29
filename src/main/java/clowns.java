import java.util.Scanner;

public class clowns {
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
        String[] inputStore = new String[100];
        int count = 0;

        while (exitFlag) { 
            String usr_input = scanner.nextLine();
            if (usr_input.equalsIgnoreCase("exit")) {
                exitFlag = false;
            }
            else if (usr_input.equalsIgnoreCase("list")) {
                System.out.println("\n  ---------------------------------");
                System.out.println("  Here is your list of clownery:");
                for (int i = 0; i < count; i++) {
                    System.out.println("  " + (i + 1) + ". " + inputStore[i]);
                }
                System.out.println("  ---------------------------------\n");
            }
            else {
                inputStore[count] = usr_input;
                System.out.println("  ---------------------------------");
                System.out.println("added: " + usr_input);
                System.out.println("  ---------------------------------\n");
                count++;
            }
        }
        
        System.out.println("  ---------------------------------\n");
        System.out.println("  Clowning complete.");
        System.out.println("  Goodbye fellow clown!");
        System.out.println("  ---------------------------------\n");

        scanner.close();
    }
}
