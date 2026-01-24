public class AddCommand extends Command {
    private Task taskToAdd;

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