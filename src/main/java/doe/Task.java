package doe;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a Todo item and whether it has been completed.
 */
public class Task {
    protected String taskName;
    protected boolean isDone;

    /**
     * Creates an incomplete task with the given name.
     *
     * @param taskName Description of the task.
     */
    public Task(String taskName) {
        this.taskName = taskName;
        this.isDone = false;
    }

    /**
     * Returns the task description.
     *
     * @return The name or description of the task.
     */
    public String getDescription() {
        return taskName;
    }

    /**
     * Returns the status icon displayed beside this task.
     *
     * @return An "x" if the task is done, or a blank space if incomplete.
     */
    public String getStatusIcon() {
        return isDone ? "x" : " ";
    }

    /**
     * Marks this task as completed.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Marks this task as incomplete.
     */
    public void markAsNotDone() {
        isDone = false;
    }

    /**
     * Creates String for file storage.
     *
     * @return File-formatted string detailing task completion status and description.
     */
    public String toFileFormat() {
        return String.valueOf(isDone) + "|" + taskName;
    }

    /**
     * Returns this task in the format used when displaying a task list.
     *
     * @return Formatted UI string indicating task status and description.
     * */
    @Override
    public String toString() {
        return "[][" + getStatusIcon() + "] " + taskName;
    }
}
