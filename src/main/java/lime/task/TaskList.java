package lime.task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Maintains the in-memory list of tasks.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Creates an empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a task list with existing tasks.
     *
     * @param tasks initial tasks
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the list.
     *
     * @param task task to add
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Removes a task from the list by index.
     *
     * @param index zero-based index
     */
    public void delete(int index) {
        assert index >= 0 && index < tasks.size() : "Index out of bounds for deletion";
        tasks.remove(index);
    }

    /**
     * Returns the task at the given index.
     *
     * @param index zero-based index
     * @return task at index
     */
    public Task get(int index) {
        assert index >= 0 && index < tasks.size() : "Index out of bounds for access";
        return tasks.get(index);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return task count
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns all tasks.
     *
     * @return list of tasks
     */
    public ArrayList<Task> getAllTasks() {
        return tasks;
    }

    /**
     * Finds tasks whose descriptions contain the keyword.
     *
     * @param keyword keyword to search for
     * @return matching tasks
     */
    public ArrayList<Task> findTasks(String keyword) {
        return tasks.stream()
                .filter(task -> task.getDescription().contains(keyword))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Sorts the tasks by their string representation.
     */
    public void sortTasks() {
        tasks.sort(Comparator.comparing(Task::toString));
    }
}
