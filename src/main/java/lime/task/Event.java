package lime.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

//The Event Task
public class Event extends Task{
    private final LocalDate from;
    private final LocalDate to;

    public Event(String description, String from, String to) {
        super(description);
        this.from = LocalDate.parse(from);
        this.to = LocalDate.parse(to);
    }

    //Gets the start time of the event
    public LocalDate getFrom() {
        return this.from;
    }

    //Gets the end time of the event
    public LocalDate getTo() {
        return this.to;
    }

    @Override
    public String toFileString() {
        return "E | " + super.toFileString() + " | " + from + " | " + to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " +
                from.format(DateTimeFormatter.ofPattern("MMM d yyyy", Locale.ENGLISH)) +
                " to: " +
                to.format(DateTimeFormatter.ofPattern("MMM d yyyy", Locale.ENGLISH)) + ")";
    }
}
