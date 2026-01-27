package lime.ui;

import lime.task.Task;
import java.util.Scanner;

//Prints out the greeting and standard responds for each command
public class Ui {
    private Scanner sc;
    private static final String HORIZONTAL_LINE = "    ____________________________________________________________";

    public Ui() {
        this.sc = new Scanner(System.in);
    }

    //Reads the user command
    public String readCommand() {
        return sc.nextLine();
    }

    //Greeting
    public void showWelcome() {
        showLine();
        System.out.println("    Hello! I'm Lime");
        System.out.println("    What can I do for you?");
        showLine();
    }

    //Ends the Chatbot
    public void showBye() {
        System.out.println("    Bye. Hope to see you again soon!");
        showLine();
    }

    //Prints the segmentatioon line
    public void showLine() {
        System.out.println(HORIZONTAL_LINE);
    }

    //Prints the error messages
    public void showError(String message) {
        System.out.println("    " + message);
    }

    //Prints the error message regarding loading history tasks
    public void showLoadingError() {
        System.out.println("    Error loading file. Starting with an empty list.");
    }

    //Prints response for a successful task adding
    public void printTaskAdded(Task task, int size) {
        System.out.println("    Got it. I've added this task:");
        System.out.println("    " + task);
        System.out.println("    Now you have " + size + " tasks in the list.");
    }

    //Prints response for a successful task deletion
    public void printTaskDeleted(Task task, int size) {
        System.out.println("    Noted. I've removed this task:");
        System.out.println("    " + task);
        System.out.println("    Now you have " + size + " tasks in the list.");
    }
}
