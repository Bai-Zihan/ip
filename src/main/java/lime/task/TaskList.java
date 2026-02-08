package lime.task;

import java.util.ArrayList;
import java.util.stream.Collectors;

//Maintains list of tasks
public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    //Adds a task to the list
    public void add(Task task) {
        tasks.add(task);
    }

    //Remove a task form the list
    public void delete(int index) {
        assert index >= 0 && index < tasks.size() : "Index out of bounds for deletion";
        tasks.remove(index);
    }

    //Extracts a task from the list with respect to its index in the list
    public Task get(int index) {
        assert index >= 0 && index < tasks.size() : "Index out of bounds for access";
        return tasks.get(index);
    }

    //Gets the current length of the list
    public int size() {
        return tasks.size();
    }

    //Extract the list of tasks as whole
    public ArrayList<Task> getAllTasks() {
        return tasks;
    }

    public ArrayList<Task> findTasks(String keyword) {
        return tasks.stream()
                .filter(task -> task.getDescription().contains(keyword))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}