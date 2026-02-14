package lime.task;

/**
 * Task representing a simple todo item.
 */
public class Todo extends Task {
    /**
     * Creates a todo task.
     *
     * @param description task description
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Serializes the todo for persistence.
     *
     * @return todo as a file string
     */
    @Override
    public String toFileString() {
        return "T | " + super.toFileString();
    }

    /**
     * Returns a display string for the todo.
     *
     * @return formatted todo string
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
