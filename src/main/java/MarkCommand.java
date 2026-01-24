public class MarkCommand extends Command {
    private int index;

    public MarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws LimeException, java.io.IOException {
        if (index < 0 || index >= tasks.size()) {
            throw new LimeException("OOPS!!! Task number does not exist.");
        }
        tasks.get(index).markAsDone();
        System.out.println("    Nice! I've marked this task as done:");
        System.out.println("    " + tasks.get(index));
        storage.save(tasks.getAllTasks());
    }
}