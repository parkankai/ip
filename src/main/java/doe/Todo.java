package doe;


/**
 * Represents a task that must be completed by a stated deadline.
 * The deadline wording is kept as entered by the user.
 */
public class Todo extends Task {

    /**
     * Creates a todo task without any deadline
     */
    public Todo(String taskName) {
        super(taskName);
    }

    @Override
    public String toFileFormat() {
        return "TODO|" + super.toFileFormat();
    }

    @Override
    public String toString() {
        return "[t][" + getStatusIcon() + "] " + taskName;
    }
}
