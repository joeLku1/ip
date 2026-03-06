package clowns;
import clowns.handlers.CommandHandler;
import clowns.handlers.FileHandler;
import clowns.task.TaskList;
import java.util.Scanner;

public class Clowns {
    private static TaskList taskList;
    private static FileHandler storage;

    public static void main(String[] args) {        
        storage = new FileHandler();
        storage.createFile();
        taskList = new TaskList(storage.loadFromFile());

        Ui ui = new Ui(new Scanner(System.in), new CommandHandler(), taskList, storage);
        ui.showWelcome();

        while (!ui.isExit()) {
            ui.processNextInput();
        }
        ui.showGoodbye();
        ui.closeScanner();
    }
}
