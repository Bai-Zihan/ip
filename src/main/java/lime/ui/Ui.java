package lime.ui;

import java.util.ArrayList;
import java.util.Scanner;

import lime.task.Task;

/**
 * Handles user-facing messages for the CLI interface.
 */
public class Ui {
    private final Scanner sc;
    private static final String HORIZONTAL_LINE = "    ____________________________________________________________";

    /**
     * Constructs a UI backed by standard input/output.
     */
    public Ui() {
        this.sc = new Scanner(System.in);
    }

    /**
     * Reads a command from standard input.
     *
     * @return raw user input
     */
    public String readCommand() {
        return sc.nextLine();
    }

    /**
     * Displays the welcome greeting.
     */
    public void showWelcome() {
        showLine();
        System.out.println("\"    Refreshing to see you! I'm Lime.\"");
        System.out.println("    I've squeezed some space for your tasks today. What can I do?");
        showLine();
    }

    /**
     * Displays the goodbye message.
     */
    public void showBye() {
        System.out.println("    Stay fresh! Hope to see you again soon!");
    }

    /**
     * Prints a horizontal separator line.
     */
    public void showLine() {
        System.out.println(HORIZONTAL_LINE);
    }

    /**
     * Prints an error message.
     *
     * @param message message to display
     */
    public void showError(String message) {
        System.out.println("    " + message);
    }

    /**
     * Prints an error message when loading tasks fails.
     */
    public void showLoadingError() {
        System.out.println("    Error loading file. Starting with an empty list.");
    }

    /**
     * Prints the response after a task is added.
     *
     * @param task task added
     * @param size current number of tasks
     */
    public void printTaskAdded(Task task, int size) {
        System.out.println("    Got it. I've squeezed this task:");
        System.out.println("    " + task);
        System.out.println("    Now you have " + size + " tasks in the bucket.");
    }

    /**
     * Prints the response after a task is deleted.
     *
     * @param task task deleted
     * @param size current number of tasks
     */
    public void printTaskDeleted(Task task, int size) {
        System.out.println("    Noted. I've removed this task:");
        System.out.println("    " + task);
        System.out.println("    Now you have " + size + " tasks in the bucket.");
    }

    /**
     * Displays tasks whose descriptions match a keyword.
     *
     * @param foundTasks matching tasks
     */
    public void showFoundTasks(ArrayList<Task> foundTasks) {
        if (foundTasks.isEmpty()) {
            System.out.println(HORIZONTAL_LINE);
            System.out.println("    No matching tasks found.");
            System.out.println(HORIZONTAL_LINE);
            return;
        }

        System.out.println(HORIZONTAL_LINE);
        System.out.println("    Here are the matching tasks in your bucket:");

        for (int i = 0; i < foundTasks.size(); i++) {
            System.out.println("    " + (i + 1) + "." + foundTasks.get(i));
        }

        System.out.println(HORIZONTAL_LINE);
    }

    /**
     * Prints the response after tasks are sorted.
     */
    public void showSortedMessage() {
        System.out.println(HORIZONTAL_LINE);
        System.out.println("    Got it! I've sorted your tasks alphabetically.");
        System.out.println(HORIZONTAL_LINE);
    }
}
