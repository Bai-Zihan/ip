package lime.command;

import lime.LimeException;
import lime.storage.Storage;
import lime.task.Task;
import lime.task.TaskList;
import lime.ui.Ui;

/**
 * Deletes a task by index and persists the change.
 */
public class DeleteCommand extends Command {
    private final int index;

    /**
     * Creates a delete command for the given index.
     *
     * @param index zero-based index of the task to delete
     */
    public DeleteCommand(int index) {
        this.index = index;
    }

    /**
     * Removes the task at the index, updates the UI, and saves the list.
     *
     * @param tasks task list to update
     * @param ui ui for user feedback
     * @param storage storage for persistence
     * @throws LimeException when the index is out of range
     * @throws java.io.IOException when saving fails
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws LimeException, java.io.IOException {
        Task removed = getTaskAtIndex(tasks);
        tasks.delete(index);
        ui.printTaskDeleted(removed, tasks.size());
        storage.save(tasks.getAllTasks());
    }

    private Task getTaskAtIndex(TaskList tasks) throws LimeException {
        if (index < 0 || index >= tasks.size()) {
            throw new LimeException("OOPS!!! Task number does not exist.");
        }
        return tasks.get(index);
    }
}
