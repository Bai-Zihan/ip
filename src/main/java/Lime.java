import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class Lime {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Lime(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (IOException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    public void run() {
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                boolean isChanged = false;

                if (fullCommand.equals("bye")) {
                    isExit = true;
                    ui.showBye();
                }
                else if (fullCommand.equals("list")) {
                    System.out.println("    Here are the tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println("    " + (i + 1) + "." + tasks.get(i).toString());
                    }
                }
                else if (fullCommand.startsWith("mark")) {
                    String[] parts = fullCommand.split(" ");
                    if (parts.length < 2) throw new LimeException("OOPS!!! Please specify which task to mark.");

                    int index = Integer.parseInt(parts[1]) - 1;
                    if (index < 0 || index >= tasks.size()) throw new LimeException("OOPS!!! Task number " + parts[1] + " does not exist.");

                    tasks.get(index).markAsDone();
                    System.out.println("    Nice! I've marked this task as done:");
                    System.out.println("    " + tasks.get(index));
                    isChanged = true;
                }
                else if (fullCommand.startsWith("unmark")) {
                    String[] parts = fullCommand.split(" ");
                    if (parts.length < 2) throw new LimeException("OOPS!!! Please specify which task to unmark.");

                    int index = Integer.parseInt(parts[1]) - 1;
                    if (index < 0 || index >= tasks.size()) throw new LimeException("OOPS!!! Task number " + parts[1] + " does not exist.");

                    tasks.get(index).markAsUndone();
                    System.out.println("    OK, I've marked this task as not done yet:");
                    System.out.println("    " + tasks.get(index));
                    isChanged = true;
                }
                else if (fullCommand.startsWith("delete")) {
                    String[] parts = fullCommand.split(" ");
                    if (parts.length < 2) throw new LimeException("OOPS!!! Please specify which task to delete.");

                    int index = Integer.parseInt(parts[1]) - 1;
                    if (index < 0 || index >= tasks.size()) throw new LimeException("OOPS!!! Task number " + parts[1] + " does not exist.");

                    Task removed = tasks.get(index);
                    tasks.delete(index);
                    ui.printTaskDeleted(removed, tasks.size());
                    isChanged = true;
                }
                else if (fullCommand.startsWith("on ")) {
                    String dateString = fullCommand.substring(3).trim();
                    if (dateString.isEmpty()) {
                        System.out.println("    OOPS!!! Please provide a date (e.g., on 2019-10-15).");
                    } else {
                        LocalDate targetDate = Parser.parseDate(dateString);
                        System.out.println("    Here are the tasks on " + targetDate.format(DateTimeFormatter.ofPattern("MMM d yyyy", Locale.ENGLISH)) + ":");

                        int count = 0;
                        for (int i = 0; i < tasks.size(); i++) {
                            Task task = tasks.get(i);
                            if (task instanceof Deadline) {
                                Deadline d = (Deadline) task;
                                if (d.getBy().equals(targetDate)) {
                                    System.out.println("    " + task);
                                    count++;
                                }
                            } else if (task instanceof Event) {
                                Event e = (Event) task;
                                if (!targetDate.isBefore(e.getFrom()) && !targetDate.isAfter(e.getTo())) {
                                    System.out.println("    " + task);
                                    count++;
                                }
                            }
                        }
                        if (count == 0) System.out.println("    No tasks found on this date.");
                    }
                }
                else {
                    if (fullCommand.startsWith("todo")) {
                        if (fullCommand.length() <= 4) throw new LimeException("OOPS!!! The description of a todo cannot be empty.");
                        if (fullCommand.charAt(4) != ' ') throw new LimeException("OOPS!!! Please insert a space after 'todo'.");
                        String description = fullCommand.substring(5).trim();
                        if (description.isEmpty()) throw new LimeException("OOPS!!! The description of a todo cannot be empty.");

                        Task newTask = new Todo(description);
                        tasks.add(newTask);
                        ui.printTaskAdded(newTask, tasks.size());
                        isChanged = true;
                    }
                    else if (fullCommand.startsWith("deadline")) {
                        int byIndex = fullCommand.indexOf("/by");
                        if (byIndex == -1) throw new LimeException("OOPS!!! Deadline must contain '/by'.");
                        if (byIndex < 9) throw new LimeException("OOPS!!! Please insert a space after 'deadline'.");
                        if (byIndex + 4 >= fullCommand.length()) throw new LimeException("OOPS!!! The time of a deadline cannot be empty.");

                        String description = fullCommand.substring(9, byIndex).trim();
                        String by = fullCommand.substring(byIndex + 4).trim();
                        if (description.isEmpty() || by.isEmpty()) throw new LimeException("OOPS!!! The description or time cannot be empty.");

                        Task newTask = new Deadline(description, by);
                        tasks.add(newTask);
                        ui.printTaskAdded(newTask, tasks.size());
                        isChanged = true;
                    }
                    else if (fullCommand.startsWith("event")) {
                        int fromIndex = fullCommand.indexOf("/from");
                        int toIndex = fullCommand.indexOf("/to");
                        if (fromIndex == -1 || toIndex == -1) throw new LimeException("OOPS!!! Event must contain both '/from' and '/to'.");
                        if (fromIndex < 6) throw new LimeException("OOPS!!! Please insert a space after 'event'.");
                        if (toIndex < fromIndex) throw new LimeException("OOPS!!! Please ensure '/from' appears before '/to'.");
                        if (toIndex < fromIndex + 6) throw new LimeException("OOPS!!! The start time cannot be empty.");
                        if (toIndex + 4 >= fullCommand.length()) throw new LimeException("OOPS!!! The end time cannot be empty.");

                        String description = fullCommand.substring(6, fromIndex).trim();
                        String from = fullCommand.substring(fromIndex + 6, toIndex).trim();
                        String to = fullCommand.substring(toIndex + 4).trim();
                        if (description.isEmpty() || from.isEmpty() || to.isEmpty()) throw new LimeException("OOPS!!! The description or time cannot be empty.");

                        Task newTask = new Event(description, from, to);
                        tasks.add(newTask);
                        ui.printTaskAdded(newTask, tasks.size());
                        isChanged = true;
                    }
                    else {
                        throw new LimeException("OOPS!!! I'm sorry, but I don't know what that means :-(");
                    }
                }

                if (isChanged) {
                    storage.save(tasks.getAllTasks());
                }

            } catch (LimeException e) {
                ui.showError(e.getMessage());
            } catch (DateTimeParseException e) {
                ui.showError("OOPS!!! Invalid date format. Please use yyyy-mm-dd (e.g., 2019-10-15).");
            } catch (NumberFormatException e) {
                ui.showError("OOPS!!! Please enter a valid number.");
            } catch (Exception e) {
                ui.showError(e.getMessage());
            } finally {
                if (!isExit) {
                    ui.showLine();
                }
            }
        }
    }

    public static void main(String[] args) {
        new Lime("./data/lime.txt").run();
    }
}