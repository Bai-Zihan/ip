package lime.command;

import lime.storage.Storage;
import lime.task.Task;
import lime.task.TaskList;
import lime.ui.Ui;

public class AddCommand extends Command {
    private final Task taskToAdd;

    public AddCommand(Task task) {
        this.taskToAdd = task;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws java.io.IOException {
        tasks.add(taskToAdd);
        ui.printTaskAdded(taskToAdd, tasks.size());
        storage.save(tasks.getAllTasks());
    }
}