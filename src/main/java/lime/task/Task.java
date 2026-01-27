package lime.task;

//Task as whole
public class Task {
    private String description;
    private boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    //Gets the mission of the task
    public String getDescription() {
        return this.description;
    }

    //Gets if the task is done
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    //Marks the task as done
    public void markAsDone() {
        this.isDone = true;
    }

    //Undo the task
    public void markAsUndone() {
        this.isDone = false;
    }

    public String toFileString() {
        return (isDone ? "1" : "0") + " | " + description;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
