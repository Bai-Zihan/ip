package lime.command;

import lime.storage.Storage;
import lime.task.TaskList;
import lime.ui.Ui;

/**
 * Lists tasks currently stored in the task list.
 */
public class ListCommand extends Command {
    /**
     * Prints all tasks to standard output.
     *
     * @param tasks task list to read from
     * @param ui ui instance (not used)
     * @param storage storage instance (not used)
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        System.out.println("    Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("    " + (i + 1) + "." + tasks.get(i));
        }
    }
}
