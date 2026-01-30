package lime.storage;

import lime.task.Task;
import lime.task.Todo;
import lime.task.Deadline;
import lime.task.Event;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

//Handles tasks savings/loadings
public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    //loads the tasks from the hard drive to the task list
    public ArrayList<Task> load() throws IOException {
        ArrayList<Task> loadedTasks = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            return loadedTasks;
        }

        Scanner fileSc = new Scanner(file);

        while (fileSc.hasNext()) {
            String line = fileSc.nextLine();
            String[] parts = line.split(" \\| ");

            Task task = switch (parts[0]) {
                case "T" -> new Todo(parts[2]);
                case "D" -> new Deadline(parts[2], parts[3]);
                case "E" -> new Event(parts[2], parts[3], parts[4]);
                default -> null;
            };

            if (task != null) {
                if (parts[1].equals("1")) {
                    task.markAsDone();
                }
                loadedTasks.add(task);
            }
        }
        fileSc.close();
        return loadedTasks;
    }

    //Saves the current list of tasks to the hard drive
    public void save(ArrayList<Task> tasks) throws IOException {
        File file = new File(filePath);
        file.getParentFile().mkdirs();

        FileWriter fw = new FileWriter(file);
        for (Task task : tasks) {
            fw.write(task.toFileString() + System.lineSeparator());
        }
        fw.close();
    }
}