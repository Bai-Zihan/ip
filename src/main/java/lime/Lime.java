package lime;

import lime.command.Command;
import lime.parser.Parser;
import lime.storage.Storage;
import lime.task.TaskList;
import lime.ui.Ui;
import java.io.IOException;
import java.time.format.DateTimeParseException;

public class Lime {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

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

            } catch (LimeException e) {
                ui.showError(e.getMessage());
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

    public static void main(String[] args) {
        new Lime("./data/lime.txt").run();
    }
}