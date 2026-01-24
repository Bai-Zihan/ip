import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Deadline extends Task{
    private LocalDate by;

    public Deadline(String description, String by) {
        super(description);
        this.by = LocalDate.parse(by);
    }

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
