import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/*
TODO|false|journal
DEADLINE|false|read|by friday 4pm
EVENT|true|cycle|from 2pm to 4pm
 */

public class ToDoManager {
    private final Path file = Path.of("todo.txt"); // location of todo.txt which stores data
    private List<Task> tasks = new ArrayList<>();

    public ToDoManager() {
        loadTodo();
    }

    public List<Task> getTasks() {
        return tasks;
    }

    private void loadTodo() {
        if (!Files.exists(file)) { // check presence of previous files
            return;
        }

        try{
            List<String> lines = Files.readAllLines(file); // reads and stores each line in a String
            for (String line : lines) {
                // edge case checker: if line is empty
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] items = line.split("\\|");
                // edge case: incorrect data
                if (items.length < 3) {
                    continue;
                }

                String taskType = items[0];
                boolean isDone = Boolean.parseBoolean(items[1]);
                String taskName = items[2];

                Task task = null;
                // Conditionally handle the 4th parameter based on task type
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

        }
        catch (IOException e) {
            System.out.println("an error occurred while loading tasks: " + e.getMessage());
        }

    }

    public void saveTodo(List<Task> currentTasks) {
        List<String> lines = new ArrayList<>();
        for (Task task : currentTasks) {
            lines.add(task.toFileFormat());
        }

        try {
            Files.write(file, lines);
        } catch (IOException e) {
            System.out.println("an error occurred while saving tasks: " + e.getMessage());
        }
    }
}
