package lime.command;

import lime.storage.Storage;
import lime.task.Task;
import lime.task.TaskList;
import lime.ui.Ui;

/**
 * Adds a task to the list and persists it.
 */
public class AddCommand extends Command {
    private final Task taskToAdd;

    /**
     * Creates an add command for the given task.
     *
     * @param task task to add
     */
    public AddCommand(Task task) {
        this.taskToAdd = task;
    }

    /**
     * Adds the task, updates the UI, and saves the list.
     *
     * @param tasks task list to update
     * @param ui ui for user feedback
     * @param storage storage for persistence
     * @throws java.io.IOException when saving fails
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws java.io.IOException {
        tasks.add(taskToAdd);
        ui.printTaskAdded(taskToAdd, tasks.size());
        storage.save(tasks.getAllTasks());
    }
}
