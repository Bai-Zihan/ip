import java.util.Scanner;
import java.util.ArrayList;

public class Lime {
    public static void main(String[] args) {
        String name = "Lime";
        String horizontalLine = "   ____________________________________________________________";
        Scanner sc = new Scanner(System.in);
        ArrayList<Task> toDo = new ArrayList<>();

        System.out.println(horizontalLine);
        System.out.println("    Hello! I'm " + name);
        System.out.println("    What can I do for you?");
        System.out.println(horizontalLine);

        String command = sc.nextLine();
        while (!command.equals("bye")) {
            System.out.println(horizontalLine);

            try {
                if (command.equals("list")) {
                    System.out.println("    Here are the tasks in your list:");
                    for (int i = 0; i < toDo.size(); i++) {
                        System.out.println("    " + (i + 1) + "." + toDo.get(i).toString());
                    }
                } else if (command.startsWith("mark")) {
                    String[] parts = command.split(" ");
                    if (parts.length < 2) {
                        throw new LimeException("OOPS!!! Please specify which task to mark.");
                    }

                    try {
                        int index = Integer.parseInt(parts[1]) - 1;

                        if (index < 0 || index >= toDo.size()) {
                            throw new LimeException("OOPS!!! Task number " + parts[1] + " does not exist.");
                        }

                        toDo.get(index).markAsDone();
                        System.out.println("    Nice! I've marked this task as done:");
                        System.out.println("    " + toDo.get(index).toString());

                    } catch (NumberFormatException e) {
                        throw new LimeException("OOPS!!! Please enter a valid number.");
                    }
                } else if (command.startsWith("unmark")) {

                    String[] parts = command.split(" ");
                    if (parts.length < 2) {
                        throw new LimeException("OOPS!!! Please specify which task to unmark.");
                    }

                    try {
                        int index = Integer.parseInt(parts[1]) - 1;
                        if (index < 0 || index >= toDo.size()) {
                            throw new LimeException("OOPS!!! Task number " + parts[1] + " does not exist.");
                        }

                        toDo.get(index).markAsUndone();
                        System.out.println("    OK, I've marked this task as not done yet:");
                        System.out.println("    " + toDo.get(index).toString());

                    } catch (NumberFormatException e) {
                        throw new LimeException("OOPS!!! Please enter a valid number.");
                    }
                } else if (command.startsWith("delete")) {
                    String[] parts = command.split(" ");
                    if (parts.length < 2) {
                        throw new LimeException("OOPS!!! Please specify which task to delete.");
                    }

                    try {
                        int index = Integer.parseInt(parts[1]) - 1;
                        if (index < 0 || index >= toDo.size()) {
                            throw new LimeException("OOPS!!! Task number " + parts[1] + " does not exist.");
                        }

                        Task taskToDelete = toDo.get(index);

                        toDo.remove(index);

                        System.out.println("    Noted. I've removed this task:");
                        System.out.println("    " + taskToDelete.toString());
                        System.out.println("    Now you have " + toDo.size() + " tasks in the list.");

                    } catch (NumberFormatException e) {
                        throw new LimeException("OOPS!!! Please enter a valid number.");
                    }
                } else {
                    if (command.startsWith("todo")) {
                        if (command.length() <= 4) {
                            throw new LimeException("OOPS!!! The description of a todo cannot be empty.");
                        }
                        if (command.charAt(4) != ' ') {
                            throw new LimeException("OOPS!!! Please insert a space after 'todo'.");
                        }
                        String description = command.substring(5);
                        if (description.isEmpty()) {
                            throw new LimeException("OOPS!!! The description of a todo cannot be empty.");
                        }

                        Task newTask = new Todo(description);
                        toDo.add(newTask);
                        System.out.println("    Got it. I've added this task:");
                        System.out.println("    " + newTask);
                        System.out.println("    Now you have " + toDo.size() + " tasks in the list.");

                    } else if (command.startsWith("deadline")) {
                        int byIndex = command.indexOf("/by");
                        if (byIndex == -1) {
                            throw new LimeException("OOPS!!! Deadline must contain '/by'.");
                        }
                        if (byIndex < 9) {
                            throw new LimeException("OOPS!!! Please insert a space after 'deadline'.");
                        }
                        if (byIndex + 4 >= command.length()) {
                            throw new LimeException("OOPS!!! The time of a deadline cannot be empty.");
                        }
                        String description = command.substring(9, byIndex);
                        String by = command.substring(byIndex + 4);
                        if (description.isEmpty() || by.isEmpty()) {
                            throw new LimeException("OOPS!!! The description or time cannot be empty.");
                        }
                        Task newTask = new Deadline(description, by);
                        toDo.add(newTask);
                        System.out.println("    Got it. I've added this task:");
                        System.out.println("    " + newTask);
                        System.out.println("    Now you have " + toDo.size() + " tasks in the list.");
                    } else if (command.startsWith("event")) {
                        int fromIndex = command.indexOf("/from");
                        int toIndex = command.indexOf("/to");
                        if (fromIndex == -1 || toIndex == -1) {
                            throw new LimeException("OOPS!!! Event must contain both '/from' and '/to'.");
                        }
                        if (fromIndex < 6) {
                            throw new LimeException("OOPS!!! Please insert a space after 'event'.");
                        }
                        if (toIndex < fromIndex) {
                            throw new LimeException("OOPS!!! Please ensure '/from' appears before '/to'.");
                        }
                        if (toIndex < fromIndex + 6) {
                            throw new LimeException("OOPS!!! The start time cannot be empty.");
                        }
                        if (toIndex + 4 >= command.length()){
                            throw new LimeException("OOPS!!! The end time cannot be empty.");
                        }
                        String description = command.substring(6, fromIndex);
                        String from = command.substring(fromIndex + 6, toIndex);
                        String to = command.substring(toIndex + 4);
                        if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
                            throw new LimeException("OOPS!!! The description or time cannot be empty.");
                        }
                        Task newTask = new Event(description, from, to);
                        toDo.add(newTask);
                        System.out.println("    Got it. I've added this task:");
                        System.out.println("    " + newTask);
                        System.out.println("    Now you have " + toDo.size() + " tasks in the list.");
                    } else {
                        throw new LimeException("OOPS!!! I'm sorry, but I don't know what that means :-(");
                    }
                }
            } catch (LimeException e){
                System.out.println("    " + e.getMessage());
            }

            System.out.println(horizontalLine);
            command = sc.nextLine();
        }

        System.out.println(horizontalLine);
        System.out.println("    Bye. Hope to see you again soon!");
        System.out.println(horizontalLine);
    }
}
