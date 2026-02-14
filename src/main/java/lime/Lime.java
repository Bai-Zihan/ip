package lime;

import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import lime.command.Command;
import lime.parser.Parser;
import lime.storage.Storage;
import lime.task.TaskList;
import lime.ui.Ui;

/**
 * Core chatbot class coordinating parsing, execution, and storage.
 */
public class Lime {

    private final Storage storage;
    private TaskList tasks;
    private final Ui ui;

    /**
     * Creates a chatbot with the given storage file path.
     *
     * @param filePath path to the storage file
     */
    public Lime(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (IOException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Runs the interactive command loop.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            String fullCommand = ui.readCommand();
            ui.showLine();
            isExit = executeCommand(fullCommand);
            ui.showLine();
        }
    }

    private boolean executeCommand(String fullCommand) {
        try {
            Command command = Parser.parse(fullCommand);
            command.execute(tasks, ui, storage);
            return command.isExit();
        } catch (DateTimeParseException e) {
            ui.showError("OOPS!!! Invalid date format. Please use yyyy-mm-dd.");
        } catch (IOException e) {
            ui.showError("OOPS!!! Error saving data: " + e.getMessage());
        } catch (Exception e) {
            ui.showError(e.getMessage());
        }
        return false;
    }

    /**
     * Generates a response for a user input in GUI mode.
     *
     * @param input raw user input
     * @return response text
     */
    public String getResponse(String input) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream previousConsole = System.out;
        System.setOut(new PrintStream(baos));

        try {
            Command c = Parser.parse(input);
            c.execute(tasks, ui, storage);
        } catch (LimeException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            System.setOut(previousConsole);
        }

        return baos.toString();
    }

    /**
     * Starts the chatbot in CLI mode.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        new Lime("./data/lime.txt").run();
    }
}
