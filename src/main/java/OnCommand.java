import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class OnCommand extends Command {
    private LocalDate targetDate;

    public OnCommand(LocalDate date) {
        this.targetDate = date;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        System.out.println("    Here are the tasks on " + targetDate.format(DateTimeFormatter.ofPattern("MMM d yyyy", Locale.ENGLISH)) + ":");
        int count = 0;
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task instanceof Deadline) {
                if (((Deadline) task).getBy().equals(targetDate)) {
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
        if (count == 0) {
            System.out.println("    No tasks found on this date.");
        }
    }
}