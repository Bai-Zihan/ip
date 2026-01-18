import java.util.Scanner;

public class Lime {
    public static void main(String[] args) {
        String name = "Lime";
        String horizontalLine = "   ____________________________________________________________";
        Scanner sc = new Scanner(System.in);

        System.out.println(horizontalLine);
        System.out.println("    Hello! I'm " + name);
        System.out.println("    What can I do for you?");
        System.out.println(horizontalLine);

        String command = sc.nextLine();
        while (!command.equals("bye")) {
            System.out.println(horizontalLine);
            System.out.println("    " + command);
            System.out.println(horizontalLine);
            command = sc.nextLine();
        }

        System.out.println(horizontalLine);
        System.out.println("    Bye. Hope to see you again soon!");
        System.out.println(horizontalLine);
    }
}
