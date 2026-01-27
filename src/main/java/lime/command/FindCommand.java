package lime.command;

import lime.ui.Ui;
import lime.storage.Storage;
import lime.task.TaskList;
import lime.task.Task;
import java.util.ArrayList;

//Finds a command containing a keyword
public class FindCommand extends Command {
    private final String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ArrayList<Task> foundTasks = tasks.findTasks(keyword);
        ui.showFoundTasks(foundTasks);
    }
}