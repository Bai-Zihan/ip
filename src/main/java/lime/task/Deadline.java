package lime.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Task representing a deadline with a due date.
 */
public class Deadline extends Task {
    private final LocalDate by;

    /**
     * Creates a deadline task.
     *
     * @param description task description
     * @param by due date in ISO-8601 format
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = LocalDate.parse(by);
    }

    /**
     * Returns the deadline date.
     *
     * @return deadline date
     */
    public LocalDate getBy() {
        return this.by;
    }

    /**
     * Serializes the deadline for persistence.
     *
     * @return deadline as a file string
     */
    @Override
    public String toFileString() {
        return "D | " + super.toFileString() + " | " + by;
    }

    /**
     * Returns a display string for the deadline.
     *
     * @return formatted deadline string
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(DateTimeFormatter.ofPattern("MMM d yyyy", Locale.ENGLISH)) + ")";
    }
}
