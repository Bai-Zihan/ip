package lime.command;

import lime.LimeException;
import lime.storage.Storage;
import lime.task.Task;
import lime.task.TaskList;
import lime.ui.Ui;

/**
 * Marks a task as not done by index.
 */
public class UnmarkCommand extends Command {
    private final int index;

    /**
     * Creates an unmark command for the given index.
     *
     * @param index zero-based index of the task to unmark
     */
    public UnmarkCommand(int index) {
        this.index = index;
    }

    /**
     * Marks the task as not done and persists the update.
     *
     * @param tasks task list to update
     * @param ui ui for user feedback
     * @param storage storage for persistence
     * @throws LimeException when the index is out of range
     * @throws java.io.IOException when saving fails
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws LimeException, java.io.IOException {
        Task task = getTaskAtIndex(tasks);
        task.markAsUndone();
        System.out.println("    OK, I've marked this task as not done yet:");
        System.out.println("    " + task);
        storage.save(tasks.getAllTasks());
    }

    private Task getTaskAtIndex(TaskList tasks) throws LimeException {
        if (index < 0 || index >= tasks.size()) {
            throw new LimeException("OOPS!!! Task number does not exist.");
        }
        return tasks.get(index);
    }
}
