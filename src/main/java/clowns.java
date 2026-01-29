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
        System.out.println("\tHello, I am clowning!");
        System.out.println("\t---------------------------------\n");

        Scanner scanner = new Scanner(System.in);
        System.out.print("\tEnter your name:\n"); 
        String name = scanner.nextLine();
        System.out.println("\nWelcome, " + name + "! Let's clown together! Enter your command below:\n");

        boolean exitFlag = true;

        while (exitFlag) { 
            String usr_input = scanner.nextLine();
            if (usr_input.equalsIgnoreCase("exit")) {
                exitFlag = false;
            } else {
                System.out.println("\t---------------------------------\n");
                System.out.println(usr_input);
                System.out.println("\t---------------------------------\n");
            }
        }

        System.out.println("\tClowning complete.");
        System.out.println("\tGoodbye fellow clown!");
        System.out.println("\t---------------------------------\n");

        scanner.close();
    }
}
