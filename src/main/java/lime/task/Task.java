package lime.task;

/**
 * Base task type with a description and completion state.
 */
public class Task {
    private final String description;
    private boolean isDone;

    /**
     * Creates a task with the given description.
     *
     * @param description task description
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the task description.
     *
     * @return task description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns the status icon used for display.
     *
     * @return status icon
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void markAsUndone() {
        this.isDone = false;
    }

    /**
     * Serializes the task for persistence.
     *
     * @return task as a file string
     */
    public String toFileString() {
        return (isDone ? "1" : "0") + " | " + description;
    }

    /**
     * Returns a display string for the task.
     *
     * @return formatted task string
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
