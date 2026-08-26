import java.util.List;

/**
 * Contains the task list; has operations to add/delete/modify tasks in the list.
 */
public class TaskList {
    private final List<Task> tasks;

    /** Initializes TaskList with an existing list of tasks (e.g., loaded from storage). */
    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public Task removeTask(int index) {
        return tasks.remove(index);
    }

    public Task getTask(int index) {
        return tasks.get(index);
    }

    public int size() {
        return tasks.size();
    }

    /** Finds a task index matching a name or numerical index string. */
    public int findTaskIndex(String removeInput) {
        int removeIndex = -1;
        // Step 1: Try treating input as an index number
        try {
            int taskNumber = Integer.parseInt(removeInput);
            if (taskNumber >= 1 && taskNumber <= tasks.size()) {
                removeIndex = taskNumber - 1;
            }
        } catch (NumberFormatException e) {
            // Fallthrough if not a number
        }

        // Step 2: Search for a matching task name description if index wasn't found
        if (removeIndex == -1) {
            for (int i = 0; i < tasks.size(); i++) {
                if (tasks.get(i).getDescription().equals(removeInput)) {
                    removeIndex = i;
                    break;
                }
            }
        }
        return removeIndex;
    }
}