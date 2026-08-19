/**
 * Represents a Todo item and whether it has been completed.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /** Creates an incomplete task with the given description. */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /** Returns the task description. */
    public String getDescription() {
        return description;
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

    /** Returns this task in the format used when displaying a task list. */
    @Override
    public String toString() {
        return "[t][" + getStatusIcon() + "] " + description;
    }
}
