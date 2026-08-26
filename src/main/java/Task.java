/**
 * Represents a Todo item and whether it has been completed.
 */
public class Task {
    protected String taskName;
    protected boolean isDone;

    /** Creates an incomplete task with the given name. */
    public Task(String taskName) {
        this.taskName = taskName;
        this.isDone = false;
    }

    /** Returns the task description. */
    public String getDescription() {
        return taskName;
    }

    /** Returns the status icon displayed beside this task. */
    public String getStatusIcon() {
        return isDone ? "x" : " ";
    }

    /** Marks this task as completed. */
    public void markAsDone() {
        isDone = true;
    }

    /** Marks this task as incomplete. */
    public void markAsNotDone() {
        isDone = false;
    }

    /** Creates String for file storage. */
    public String toFileFormat(){
        return String.valueOf(isDone) + "|" + taskName;
    }

    /** Returns this task in the format used when displaying a task list. */
    @Override
    public String toString() {
        return "[][" + getStatusIcon() + "] " + taskName;
    }
}
