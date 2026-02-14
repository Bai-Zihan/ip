package lime.command;

import lime.storage.Storage;
import lime.task.TaskList;
import lime.ui.Ui;

/**
 * Ends the application session.
 */
public class ExitCommand extends Command {
    /**
     * Displays the goodbye message.
     *
     * @param tasks task list (not used)
     * @param ui ui for user feedback
     * @param storage storage instance (not used)
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showBye();
    }

    /**
     * Indicates this command should exit the main loop.
     *
     * @return true to signal exit
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
