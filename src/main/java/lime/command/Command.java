package lime.command;

import lime.LimeException;
import lime.storage.Storage;
import lime.task.TaskList;
import lime.ui.Ui;

/**
 * Base type for executable commands.
 */
public abstract class Command {
    /**
     * Executes the command against the task list.
     *
     * @param tasks task list to operate on
     * @param ui ui for user feedback
     * @param storage storage for persistence
     * @throws LimeException when command execution fails
     * @throws java.io.IOException when saving fails
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws LimeException, java.io.IOException;

    /**
     * Indicates whether this command should exit the app loop.
     *
     * @return true if this command exits
     */
    public boolean isExit() {
        return false;
    }
}
