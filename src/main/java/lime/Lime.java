package lime;

import lime.command.Command;
import lime.parser.Parser;
import lime.storage.Storage;
import lime.task.TaskList;
import lime.ui.Ui;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

//The Chatbot class
public class Lime {

    private final Storage storage;
    private TaskList tasks;
    private final Ui ui;

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

    //Operates the Chatbot
    public void run() {
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();

                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();

            } catch (DateTimeParseException e) {
                ui.showError("OOPS!!! Invalid date format. Please use yyyy-mm-dd.");
            } catch (IOException e) {
                ui.showError("OOPS!!! Error saving data: " + e.getMessage());
            } catch (Exception e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    //Generate a response for the user's chat message.
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

    //Specifies the hard drive address and runs the Chatbot
    public static void main(String[] args) {
        new Lime("./data/lime.txt").run();
    }
}