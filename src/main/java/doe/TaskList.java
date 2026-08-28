package doe;


import java.util.List;

/**
 * Contains the task list; has operations to add/delete/modify tasks in the list.
 */
public class TaskList {
    private final List<Task> tasks;

    /**
     * Initializes TaskList with an existing list of tasks (e.g., loaded from storage).
     *
     * @param tasks A list of tasks typically loaded from storage.
     */
    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Returns the complete list of stored tasks.
     *
     * @return A list containing all Task objects.
     */
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * Adds a new task to the task list.
     *
     * @param task The Task object to add.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Removes a task from the task list at a specified index.
     *
     * @param index The zero-based index of the task to remove.
     * @return The Task object that was removed.
     */
    public Task removeTask(int index) {
        return tasks.remove(index);
    }

    /**
     * Returns a task from the list given its index.
     *
     * @param index The zero-based index of the task to retrieve.
     * @return The Task object at the specified index.
     */
    public Task getTask(int index) {
        return tasks.get(index);
    }

    /**
     * Returns the total number of tasks in the list.
     *
     * @return The size of the task list.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Finds a task index matching a name or numerical index string.
     *
     * @param removeInput The user input representing either the task's index number or its description.
     * @return The zero-based index of the matching task, or -1 if no match is found.
     */
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