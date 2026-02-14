package lime.command;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import lime.storage.Storage;
import lime.task.Deadline;
import lime.task.Event;
import lime.task.Task;
import lime.task.TaskList;
import lime.ui.Ui;

/**
 * Lists tasks that occur on a specific date.
 */
public class OnCommand extends Command {
    private final LocalDate targetDate;

    /**
     * Creates an on command for the given date.
     *
     * @param date date to match against tasks
     */
    public OnCommand(LocalDate date) {
        this.targetDate = date;
    }

    /**
     * Prints tasks that fall on the target date.
     *
     * @param tasks task list to search
     * @param ui ui instance (not used)
     * @param storage storage instance (not used)
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        String formattedDate = targetDate.format(DateTimeFormatter.ofPattern("MMM d yyyy", Locale.ENGLISH));
        System.out.println("    Here are the tasks on " + formattedDate + ":");
        int count = 0;
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (!isTaskOnDate(task)) {
                continue;
            }
            System.out.println("    " + task);
            count++;
        }
        if (count == 0) {
            System.out.println("    No tasks found on this date.");
        }
    }

    private boolean isTaskOnDate(Task task) {
        if (task instanceof Deadline deadline) {
            return deadline.getBy().equals(targetDate);
        }
        if (task instanceof Event event) {
            return !targetDate.isBefore(event.getFrom()) && !targetDate.isAfter(event.getTo());
        }
        return false;
    }
}
