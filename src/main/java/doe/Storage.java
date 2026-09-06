package doe;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Deals with loading tasks from the file and saving tasks in the file.
 */
public class Storage {
    private final Path filePath;

    /**
     * Creates a new Storage instance initialised with specific file path.
     *
     * @param filePath The file path location for storage.
     */
    public Storage(String filePath) {
        this.filePath = Path.of(filePath);
    }

    /**
     * Loads tasks from the text file, parsing each line back into Task objects.
     *
     * @return A list of tasks retrieved from the storage file.
     */
    public List<Task> load() {
        List<Task> tasks = new ArrayList<>();
        if (!Files.exists(filePath)) {
            return tasks;
        }

        try {
            List<String> lines = Files.readAllLines(filePath);
            for (String line : lines) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] items = line.split("\\|");
                if (items.length < 3) {
                    continue;
                }

                String taskType = items[0];
                boolean isDone = Boolean.parseBoolean(items[1]);
                String taskName = items[2];

                Task task = null;
                if (taskType.equals("TODO")) {
                    task = new Todo(taskName);
                } else if (taskType.equals("DEADLINE") && items.length > 3) {
                    task = new Deadline(taskName, items[3]);
                } else if (taskType.equals("EVENT") && items.length > 3) {
                    task = new Event(taskName, items[3]);
                }

                if (task != null) {
                    if (isDone) {
                        task.markAsDone();
                    }
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            System.out.println("an error occurred while loading tasks: " + e.getMessage());
        }
        return tasks;
    }

    /**
     * Saves the current list of tasks to the text file format.
     *
     * @param currentTasks The list of tasks to be saved in storage.
     */
    public void save(List<Task> currentTasks) {
        List<String> lines = currentTasks.stream()
                .map(Task::toFileFormat)
                .toList();

        try {
            Files.write(filePath, lines);
        } catch (IOException e) {
            System.out.println("an error occurred while saving tasks: " + e.getMessage());
        }
    }
}
