public class DeleteCommand extends Command {
    private int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws LimeException, java.io.IOException {
        if (index < 0 || index >= tasks.size()) {
            throw new LimeException("OOPS!!! Task number does not exist.");
        }
        Task removed = tasks.get(index);
        tasks.delete(index);
        ui.printTaskDeleted(removed, tasks.size());
        storage.save(tasks.getAllTasks());
    }
}