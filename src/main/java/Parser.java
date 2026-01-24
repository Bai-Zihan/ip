import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Parser {
    public static LocalDate parseDate(String dateString) throws DateTimeParseException {
        return LocalDate.parse(dateString);
    }
}