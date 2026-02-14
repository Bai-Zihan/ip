package lime.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import lime.task.Deadline;
import lime.task.Event;
import lime.task.Task;
import lime.task.Todo;

/**
 * Handles loading and saving tasks to disk.
 */
public class Storage {
    private final String filePath;

    /**
     * Creates a storage helper for the given file path.
     *
     * @param filePath path to the storage file
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from disk.
     *
     * @return list of loaded tasks
     * @throws IOException when reading fails
     */
    public ArrayList<Task> load() throws IOException {
        assert filePath != null : "File path should not be null during load";
        ArrayList<Task> loadedTasks = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            return loadedTasks;
        }

        try (Scanner fileSc = new Scanner(file)) {
            while (fileSc.hasNext()) {
                String line = fileSc.nextLine();
                String[] parts = line.split(" \\| ");
                Task task = parseTask(parts);
                if (task == null) {
                    continue;
                }
                if ("1".equals(parts[1])) {
                    task.markAsDone();
                }
                loadedTasks.add(task);
            }
        }
        return loadedTasks;
    }

    private Task parseTask(String[] parts) {
        return switch (parts[0]) {
            case "T" -> new Todo(parts[2]);
            case "D" -> new Deadline(parts[2], parts[3]);
            case "E" -> new Event(parts[2], parts[3], parts[4]);
            default -> null;
        };
    }

    /**
     * Saves the current list of tasks to disk.
     *
     * @param tasks tasks to persist
     * @throws IOException when writing fails
     */
    public void save(ArrayList<Task> tasks) throws IOException {
        File file = new File(filePath);
        file.getParentFile().mkdirs();

        try (FileWriter fw = new FileWriter(file)) {
            for (Task task : tasks) {
                fw.write(task.toFileString() + System.lineSeparator());
            }
        }
    }
}
