package lime.ui;

import lime.task.Task;
import java.util.Scanner;

public class Ui {
    private Scanner sc;
    private static final String HORIZONTAL_LINE = "    ____________________________________________________________";

    public Ui() {
        this.sc = new Scanner(System.in);
    }

    public String readCommand() {
        return sc.nextLine();
    }

    public void showWelcome() {
        showLine();
        System.out.println("    Hello! I'm Lime");
        System.out.println("    What can I do for you?");
        showLine();
    }

    public void showBye() {
        System.out.println("    Bye. Hope to see you again soon!");
        showLine();
    }

    public void showLine() {
        System.out.println(HORIZONTAL_LINE);
    }

    public void showError(String message) {
        System.out.println("    " + message);
    }

    public void showLoadingError() {
        System.out.println("    Error loading file. Starting with an empty list.");
    }

    public void printTaskAdded(Task task, int size) {
        System.out.println("    Got it. I've added this task:");
        System.out.println("    " + task);
        System.out.println("    Now you have " + size + " tasks in the list.");
    }

    public void printTaskDeleted(Task task, int size) {
        System.out.println("    Noted. I've removed this task:");
        System.out.println("    " + task);
        System.out.println("    Now you have " + size + " tasks in the list.");
    }
}
