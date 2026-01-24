package lime.command;

import lime.LimeException;
import lime.storage.Storage;
import lime.task.TaskList;
import lime.ui.Ui;
import java.io.IOException;

public abstract class Command {
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws LimeException, java.io.IOException;

    public boolean isExit() {
        return false;
    }
}