import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Parser {

    public static Command parse(String fullCommand) throws LimeException {
        String commandWord = fullCommand.split(" ")[0];

        if (fullCommand.equals("bye")) {
            return new ExitCommand();
        }

        if (fullCommand.equals("list")) {
            return new ListCommand();
        }

        if (commandWord.equals("mark")) {
            return new MarkCommand(parseIndex(fullCommand));
        }

        if (commandWord.equals("unmark")) {
            return new UnmarkCommand(parseIndex(fullCommand));
        }

        if (commandWord.equals("delete")) {
            return new DeleteCommand(parseIndex(fullCommand));
        }

        if (commandWord.equals("todo")) {
            if (fullCommand.length() <= 4) {
                throw new LimeException("OOPS!!! The description of a todo cannot be empty.");
            }
            if (fullCommand.charAt(4) != ' ') {
                throw new LimeException("OOPS!!! Please insert a space after 'todo'.");
            }

            String description = fullCommand.substring(5).trim();
            if (description.isEmpty()) {
                throw new LimeException("OOPS!!! The description of a todo cannot be empty.");
            }

            return new AddCommand(new Todo(description));
        }

        if (commandWord.equals("deadline")) {
            int byIndex = fullCommand.indexOf("/by");
            if (byIndex == -1) {
                throw new LimeException("OOPS!!! Deadline must contain '/by'.");
            }

            if (byIndex < 9) {
                if (fullCommand.length() > 8 && fullCommand.charAt(8) != ' ') {
                    throw new LimeException("OOPS!!! Please insert a space after 'deadline'.");
                }
                throw new LimeException("OOPS!!! The description of a deadline cannot be empty.");
            }

            if (byIndex > 0 && fullCommand.charAt(byIndex - 1) != ' ') {
                throw new LimeException("OOPS!!! Please insert a space before '/by'.");
            }

            String description = fullCommand.substring(9, byIndex).trim();
            if (description.isEmpty()) {
                throw new LimeException("OOPS!!! The description of a deadline cannot be empty.");
            }

            if (fullCommand.length() <= byIndex + 4) {
                throw new LimeException("OOPS!!! The time of a deadline cannot be empty.");
            }
            String by = fullCommand.substring(byIndex + 4).trim();
            if (by.isEmpty()) {
                throw new LimeException("OOPS!!! The time of a deadline cannot be empty.");
            }

            return new AddCommand(new Deadline(description, by));
        }

        if (commandWord.equals("event")) {
            int fromIndex = fullCommand.indexOf("/from");
            int toIndex = fullCommand.indexOf("/to");

            if (fromIndex == -1 || toIndex == -1) {
                throw new LimeException("OOPS!!! Event must contain both '/from' and '/to'.");
            }

            if (toIndex < fromIndex) {
                throw new LimeException("OOPS!!! Please ensure '/from' appears before '/to'.");
            }

            if (fromIndex < 6) {
                if (fullCommand.length() > 5 && fullCommand.charAt(5) != ' ') {
                    throw new LimeException("OOPS!!! Please insert a space after 'event'.");
                }
                throw new LimeException("OOPS!!! The description of an event cannot be empty.");
            }

            if (fromIndex > 0 && fullCommand.charAt(fromIndex - 1) != ' ') {
                throw new LimeException("OOPS!!! Please insert a space before '/from'.");
            }

            if (toIndex > 0 && fullCommand.charAt(toIndex - 1) != ' ') {
                throw new LimeException("OOPS!!! Please insert a space before '/to'.");
            }

            String description = fullCommand.substring(6, fromIndex).trim();
            if (description.isEmpty()) {
                throw new LimeException("OOPS!!! The description of an event cannot be empty.");
            }

            if (toIndex < fromIndex + 6) {
                throw new LimeException("OOPS!!! The start time cannot be empty.");
            }
            String from = fullCommand.substring(fromIndex + 6, toIndex).trim();
            if (from.isEmpty()) {
                throw new LimeException("OOPS!!! The start time cannot be empty.");
            }

            if (fullCommand.length() <= toIndex + 4) {
                throw new LimeException("OOPS!!! The end time cannot be empty.");
            }
            String to = fullCommand.substring(toIndex + 4).trim();
            if (to.isEmpty()) {
                throw new LimeException("OOPS!!! The end time cannot be empty.");
            }

            return new AddCommand(new Event(description, from, to));
        }

        if (fullCommand.startsWith("on ")) {
            String dateString = fullCommand.substring(3).trim();
            if (dateString.isEmpty()) throw new LimeException("OOPS!!! Please provide a date.");
            try {
                return new OnCommand(LocalDate.parse(dateString));
            } catch (DateTimeParseException e) {
                throw new LimeException("OOPS!!! Invalid date format. Please use yyyy-mm-dd.");
            }
        }

        throw new LimeException("OOPS!!! I'm sorry, but I don't know what that means :-(");
    }

    private static int parseIndex(String command) throws LimeException {
        String[] parts = command.split(" ");
        if (parts.length < 2) {
            throw new LimeException("OOPS!!! Please specify a task number.");
        }
        try {
            return Integer.parseInt(parts[1]) - 1;
        } catch (NumberFormatException e) {
            throw new LimeException("OOPS!!! Please enter a valid number.");
        }
    }
}