package lime.command;

import lime.LimeException;
import lime.storage.Storage;
import lime.task.Task;
import lime.task.TaskList;
import lime.ui.Ui;
import java.io.IOException;

public class ExitCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showBye();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}