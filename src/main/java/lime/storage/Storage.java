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

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

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

            Task task = null;
            switch (parts[0]) {
                case "T":
                    task = new Todo(parts[2]);
                    break;
                case "D":
                    task = new Deadline(parts[2], parts[3]);
                    break;
                case "E":
                    task = new Event(parts[2], parts[3], parts[4]);
                    break;
            }

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