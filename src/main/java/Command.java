public abstract class Command {
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws LimeException, java.io.IOException;

    public boolean isExit() {
        return false;
    }
}