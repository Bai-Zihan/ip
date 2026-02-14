package lime.command;

import lime.storage.Storage;
import lime.task.TaskList;
import lime.ui.Ui;

/**
 * Sorts tasks alphabetically and persists the updated order.
 */
public class SortCommand extends Command {
    /**
     * Executes the sort operation and saves the result.
     *
     * @param tasks task list to sort
     * @param ui ui for user feedback
     * @param storage storage for persistence
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        tasks.sortTasks();
        ui.showSortedMessage();
        saveTasks(tasks, storage, ui);
    }

    private void saveTasks(TaskList tasks, Storage storage, Ui ui) {
        try {
            storage.save(tasks.getAllTasks());
        } catch (java.io.IOException e) {
            ui.showError("Failed to save sorted tasks.");
        }
    }
}
