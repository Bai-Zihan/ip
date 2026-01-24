import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeParseException;

public class Lime {

    private static final String FILE_PATH = "./data/lime.txt";

    public static void main(String[] args) {
        String name = "Lime";
        String horizontalLine = "   ____________________________________________________________";
        Scanner sc = new Scanner(System.in);
        ArrayList<Task> toDo = loadTasks();

        System.out.println(horizontalLine);
        System.out.println("    Hello! I'm " + name);
        System.out.println("    What can I do for you?");
        System.out.println(horizontalLine);

        String command = sc.nextLine();
        while (!command.equals("bye")) {
            System.out.println(horizontalLine);

            try {

                boolean isChanged = false;

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
                        isChanged = true;

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
                        isChanged = true;

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
                        isChanged = true;

                    } catch (NumberFormatException e) {
                        throw new LimeException("OOPS!!! Please enter a valid number.");
                    }
                } else if (command.startsWith("on ")) {
                    // 1. 截取并清理日期字符串
                    String dateString = command.substring(3).trim();

                    if (dateString.isEmpty()) {
                        System.out.println("    OOPS!!! Please provide a date (e.g., on 2019-10-15).");
                    }

                    try {
                        // 2. 解析目标日期
                        LocalDate targetDate = LocalDate.parse(dateString);

                        System.out.println("    Here are the tasks on " + targetDate.format(DateTimeFormatter.ofPattern("MMM d yyyy", Locale.ENGLISH)) + ":");

                        int count = 0;
                        for (Task task : toDo) {
                            if (task instanceof Deadline) {
                                // 3. 检查 Deadline: 截止日期是否正好是这一天
                                Deadline d = (Deadline) task;
                                if (d.getBy().equals(targetDate)) {
                                    System.out.println("    " + task);
                                    count++;
                                }
                            } else if (task instanceof Event) {
                                // 4. 检查 Event: 这一天是否在活动范围内 (包含开始和结束当天)
                                Event e = (Event) task;
                                // 逻辑：目标日期 >= 开始日期 并且 目标日期 <= 结束日期
                                if (!targetDate.isBefore(e.getFrom()) && !targetDate.isAfter(e.getTo())) {
                                    System.out.println("    " + task);
                                    count++;
                                }
                            }
                        }

                        if (count == 0) {
                            System.out.println("    No tasks found on this date.");
                        }

                    } catch (DateTimeParseException e) {
                        System.out.println("    OOPS!!! Invalid date format. Please use yyyy-mm-dd (e.g., on 2019-10-15).");
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
                        isChanged = true;

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
                        String by = command.substring(byIndex + 4).trim();
                        if (description.isEmpty() || by.isEmpty()) {
                            throw new LimeException("OOPS!!! The description or time cannot be empty.");
                        }
                        Task newTask = new Deadline(description, by);
                        toDo.add(newTask);
                        System.out.println("    Got it. I've added this task:");
                        System.out.println("    " + newTask);
                        System.out.println("    Now you have " + toDo.size() + " tasks in the list.");
                        isChanged = true;
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
                        String from = command.substring(fromIndex + 6, toIndex).trim();
                        String to = command.substring(toIndex + 4).trim();
                        if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
                            throw new LimeException("OOPS!!! The description or time cannot be empty.");
                        }
                        Task newTask = new Event(description, from, to);
                        toDo.add(newTask);
                        System.out.println("    Got it. I've added this task:");
                        System.out.println("    " + newTask);
                        System.out.println("    Now you have " + toDo.size() + " tasks in the list.");
                        isChanged = true;
                    } else {
                        throw new LimeException("OOPS!!! I'm sorry, but I don't know what that means :-(");
                    }
                }
                if (isChanged) {
                    saveTasks(toDo);
                }
            }
            catch (DateTimeParseException e) {
                System.out.println("    OOPS!!! Invalid date format. Please use yyyy-mm-dd (e.g., 2019-10-15).");
            }
            catch (Exception e){
                System.out.println("    " + e.getMessage());
            }

            System.out.println(horizontalLine);
            command = sc.nextLine();
        }

        System.out.println(horizontalLine);
        System.out.println("    Bye. Hope to see you again soon!");
        System.out.println(horizontalLine);
    }

    private static void saveTasks(ArrayList<Task> tasks) throws IOException {
        File file = new File(FILE_PATH);
        file.getParentFile().mkdirs();

        FileWriter fw = new FileWriter(file);
        for (Task task : tasks) {
            fw.write(task.toFileString() + System.lineSeparator());
        }
        fw.close();
    }

    private static ArrayList<Task> loadTasks() {
        ArrayList<Task> loadedTasks = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return loadedTasks;
        }

        try {
            Scanner fileSc = new Scanner(file);
            while (fileSc.hasNext()) {
                String line = fileSc.nextLine();
                String[] parts = line.split(" \\| ");

                Task task = null;
                switch (parts[0]) {
                    case "T":
                        task = new Todo(parts[2]);
                        break;
                    case "D":
                        task = new Deadline(parts[2], parts[3]);
                        break;
                    case "E":
                        task = new Event(parts[2], parts[3], parts[4]);
                        break;
                }

                if (task != null) {
                    if (parts[1].equals("1")) {
                        task.markAsDone();
                    }
                    loadedTasks.add(task);
                }
            }
            fileSc.close();
        } catch (IOException e) {
            System.out.println("    Error loading file: " + e.getMessage());
        }
        return loadedTasks;
    }
}
