package lime.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

//Deadline Task
public class Deadline extends Task{
    private LocalDate by;

    public Deadline(String description, String by) {
        super(description);
        this.by = LocalDate.parse(by);
    }

    //Gets the deadline time
    public LocalDate getBy() {
        return this.by;
    }

    @Override
    public String toFileString() {
        return "D | " + super.toFileString() + " | " + by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(DateTimeFormatter.ofPattern("MMM d yyyy", Locale.ENGLISH)) + ")";
    }
}
