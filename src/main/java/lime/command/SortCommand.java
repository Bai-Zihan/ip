package lime.command;

import lime.storage.Storage;
import lime.task.TaskList;
import lime.ui.Ui;

public class SortCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        tasks.sortTasks();
        ui.showSortedMessage();

        try {
            storage.save(tasks.getAllTasks());
        } catch (java.io.IOException e) {
            ui.showError("Failed to save sorted tasks.");
        }

    }
}