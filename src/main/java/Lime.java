import java.util.Scanner;

public class Lime {
    public static void main(String[] args) {
        String name = "Lime";
        String horizontalLine = "   ____________________________________________________________";
        Scanner sc = new Scanner(System.in);
        Task[] toDo = new Task[100];
        int count = 1;

        System.out.println(horizontalLine);
        System.out.println("    Hello! I'm " + name);
        System.out.println("    What can I do for you?");
        System.out.println(horizontalLine);

        String command = sc.nextLine();
        while (!command.equals("bye")) {
            System.out.println(horizontalLine);

            if (command.equals("list")) {
                System.out.println("    Here are the tasks in your list:");
                for (int i = 0; i < toDo.length; i++) {
                    if (toDo[i] == null) {
                        break;
                    }
                    System.out.println("    " + (i + 1) + "." + toDo[i].toString());
                }
            } else if (command.startsWith("mark")) {
                int index = Integer.parseInt(command.split(" ")[1]) - 1;
                toDo[index].markAsDone();

                System.out.println("    Nice! I've marked this task as done:");
                System.out.println("    " + toDo[index].toString());
            } else if (command.startsWith("unmark")) {
                int index = Integer.parseInt(command.split(" ")[1]) - 1;
                toDo[index].markAsUndone();

                System.out.println("    OK, I've marked this task as not done yet:");
                System.out.println("    " + toDo[index].toString());
            } else {
                if (command.startsWith("todo")) {
                    String description = command.substring(5);
                    toDo[count - 1] = new Todo(description);
                }
                else if (command.startsWith("deadline")) {
                    int byIndex = command.indexOf("/by");
                    String description = command.substring(9, byIndex);
                    String by = command.substring(byIndex + 4);
                    toDo[count - 1] = new Deadline(description, by);
                }
                else if (command.startsWith("event")) {
                    int fromIndex = command.indexOf("/from");
                    int toIndex = command.indexOf("/to");
                    String description = command.substring(6, fromIndex);
                    String from = command.substring(fromIndex + 6, toIndex);
                    String to = command.substring(toIndex + 4);
                    toDo[count - 1] = new Event(description, from, to);
                }

                System.out.println("    Got it. I've added this task:");
                System.out.println("    " + toDo[count - 1].toString());
                System.out.println("    Now you have " + count + " tasks in the list.");
                count++;
            }

            System.out.println(horizontalLine);
            command = sc.nextLine();
        }

        System.out.println(horizontalLine);
        System.out.println("    Bye. Hope to see you again soon!");
        System.out.println(horizontalLine);
    }
}
