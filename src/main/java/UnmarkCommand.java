public class UnmarkCommand extends Command {
    private int index;

    public UnmarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws LimeException, java.io.IOException {
        if (index < 0 || index >= tasks.size()) {
            throw new LimeException("OOPS!!! Task number does not exist.");
        }
        tasks.get(index).markAsUndone();
        System.out.println("    OK, I've marked this task as not done yet:");
        System.out.println("    " + tasks.get(index));
        storage.save(tasks.getAllTasks());
    }
}