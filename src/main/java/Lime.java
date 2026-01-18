import java.util.Scanner;

public class Lime {
    public static void main(String[] args) {
        String name = "Lime";
        String horizontalLine = "   ____________________________________________________________";
        Scanner sc = new Scanner(System.in);
        String[] toDo = new String[100];
        int count = 1;

        System.out.println(horizontalLine);
        System.out.println("    Hello! I'm " + name);
        System.out.println("    What can I do for you?");
        System.out.println(horizontalLine);

        String command = sc.nextLine();
        while (!command.equals("bye")) {
            System.out.println(horizontalLine);

            if (command.equals("list")) {
                for (int i = 0; i < toDo.length; i++) {
                    if (toDo[i] == null) {
                        break;
                    }
                    System.out.println("     " + (i + 1) + ". " + toDo[i]);                }
            } else {
                System.out.println("    " + "added: " + command);
                toDo[count - 1] = command;
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
