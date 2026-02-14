package lime.parser;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import lime.LimeException;
import lime.command.*;
import lime.task.Deadline;
import lime.task.Event;
import lime.task.Todo;


/**
 * Parses raw user input into executable command objects.
 */
public class Parser {

    /**
     * Chooses the correct command to execute or throws an exception for invalid commands.
     *
     * @param fullCommand raw user input
     * @return command to execute
     * @throws LimeException when the command is invalid or incomplete
     */
    public static Command parse(String fullCommand) throws LimeException {
        String commandWord = fullCommand.split(" ")[0];

        if (fullCommand.equals("bye")) {
            return new ExitCommand();
        }

        if (fullCommand.equals("list")) {
            return new ListCommand();
        }

        return switch (commandWord) {
            case "mark" -> new MarkCommand(parseIndex(fullCommand));
            case "unmark" -> new UnmarkCommand(parseIndex(fullCommand));
            case "delete" -> new DeleteCommand(parseIndex(fullCommand));
            case "todo" -> prepareTodo(fullCommand);
            case "deadline" -> prepareDeadline(fullCommand);
            case "event" -> prepareEvent(fullCommand);
            case "find" -> prepareFind(fullCommand);
            case "on" -> prepareOn(fullCommand);
            case "sort" -> new SortCommand();
            default -> throw new LimeException("OOPS!!! I'm sorry, but I don't know what that means :-(");
        };
    }

    /**
     * Parses a todo command.
     *
     * @param fullCommand raw user input
     * @return add command for a todo task
     * @throws LimeException when the todo description is missing or malformed
     */
    private static Command prepareTodo(String fullCommand) throws LimeException {
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

    /**
     * Parses a deadline command.
     *
     * @param fullCommand raw user input
     * @return add command for a deadline task
     * @throws LimeException when the deadline description or time is missing or malformed
     */
    private static Command prepareDeadline(String fullCommand) throws LimeException {
        int byIndex = getByIndex(fullCommand);

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

    /**
     * Parses an event command.
     *
     * @param fullCommand raw user input
     * @return add command for an event task
     * @throws LimeException when the event fields are missing or malformed
     */
    private static Command prepareEvent(String fullCommand) throws LimeException {
        int fromIndex = fullCommand.indexOf("/from");
        int toIndex = getToIndex(fullCommand, fromIndex);

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

    /**
     * Parses a find command.
     *
     * @param fullCommand raw user input
     * @return find command for the keyword
     * @throws LimeException when the keyword is missing or malformed
     */
    private static Command prepareFind(String fullCommand) throws LimeException {
        if (fullCommand.length() <= 4) {
            throw new LimeException("OOPS!!! The keyword to find cannot be empty.");
        }

        if (fullCommand.charAt(4) != ' ') {
            throw new LimeException("OOPS!!! Please insert a space after 'find'.");
        }

        String keyword = fullCommand.substring(5).trim();
        if (keyword.isEmpty()) {
            throw new LimeException("OOPS!!! The keyword to find cannot be empty.");
        }

        return new FindCommand(keyword);
    }

    /**
     * Parses an on command for a specific date.
     *
     * @param fullCommand raw user input
     * @return on command for the date
     * @throws LimeException when the date is missing or invalid
     */
    private static Command prepareOn(String fullCommand) throws LimeException {
        String dateString = fullCommand.substring(2).trim(); // remove leading "on "
        if (dateString.isEmpty()) throw new LimeException("OOPS!!! Please provide a date.");

        try {
            return new OnCommand(LocalDate.parse(dateString));
        } catch (DateTimeParseException e) {
            throw new LimeException("OOPS!!! Invalid date format. Please use yyyy-mm-dd.");
        }
    }

    /**
     * Gets the index of the "/to" segment in an event command.
     *
     * @param fullCommand raw user input
     * @param fromIndex index of the "/from" segment
     * @return index of the "/to" segment
     * @throws LimeException when the event segments are missing or misordered
     */
    private static int getToIndex(String fullCommand, int fromIndex) throws LimeException {
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

        if (fullCommand.charAt(fromIndex - 1) != ' ') {
            throw new LimeException("OOPS!!! Please insert a space before '/from'.");
        }

        if (fullCommand.charAt(toIndex - 1) != ' ') {
            throw new LimeException("OOPS!!! Please insert a space before '/to'.");
        }
        return toIndex;
    }

    /**
     * Gets the index of the "/by" segment in a deadline command.
     *
     * @param fullCommand raw user input
     * @return index of the "/by" segment
     * @throws LimeException when the deadline segment is missing or malformed
     */
    private static int getByIndex(String fullCommand) throws LimeException {
        int byIndex = fullCommand.indexOf("/by");
        if (byIndex == -1) {
            throw new LimeException("OOPS!!! Deadline must contain '/by'.");
        }

        assert byIndex >= 0 : "byIndex should be a non-negative integer.";

        if (byIndex < 9) {
            if (fullCommand.length() > 8 && fullCommand.charAt(8) != ' ') {
                throw new LimeException("OOPS!!! Please insert a space after 'deadline'.");
            }
            throw new LimeException("OOPS!!! The description of a deadline cannot be empty.");
        }

        if (fullCommand.charAt(byIndex - 1) != ' ') {
            throw new LimeException("OOPS!!! Please insert a space before '/by'.");
        }
        return byIndex;
    }

    /**
     * Locates the task index for index-specific commands.
     *
     * @param command raw user input
     * @return zero-based task index
     * @throws LimeException when the index is missing or not a number
     */
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
