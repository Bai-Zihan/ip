package lime.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Task representing an event with a start and end date.
 */
public class Event extends Task {
    private final LocalDate from;
    private final LocalDate to;

    /**
     * Creates an event task.
     *
     * @param description task description
     * @param from start date in ISO-8601 format
     * @param to end date in ISO-8601 format
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = LocalDate.parse(from);
        this.to = LocalDate.parse(to);
    }

    /**
     * Returns the start date of the event.
     *
     * @return start date
     */
    public LocalDate getFrom() {
        return this.from;
    }

    /**
     * Returns the end date of the event.
     *
     * @return end date
     */
    public LocalDate getTo() {
        return this.to;
    }

    /**
     * Serializes the event for persistence.
     *
     * @return event as a file string
     */
    @Override
    public String toFileString() {
        return "E | " + super.toFileString() + " | " + from + " | " + to;
    }

    /**
     * Returns a display string for the event.
     *
     * @return formatted event string
     */
    @Override
    public String toString() {

        return "[E]" + super.toString() + " (from: " +
                from.format(DateTimeFormatter.ofPattern("MMM d yyyy", Locale.ENGLISH)) +
                " to: " +
                to.format(DateTimeFormatter.ofPattern("MMM d yyyy", Locale.ENGLISH)) + ")";
    }
}
