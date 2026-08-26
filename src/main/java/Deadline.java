/**
 * Represents a task that must be completed by a stated deadline.
 * The deadline wording is kept as entered by the user.
 */
public class Deadline extends Task {
    /** The user-provided deadline note for this task. */
    private final String deadline;

    /** Creates an incomplete deadline task with its description and deadline note. */
    public Deadline(String taskName, String deadline) {
        super(taskName);
        this.deadline = deadline;
    }

    @Override
    public String toFileFormat(){
        return "DEADLINE|"  + super.toFileFormat() + "|" + deadline;
    }
    /** Returns this deadline in the format used when displaying a task list. */
    @Override
    public String toString() {
        return "[d][" + getStatusIcon() + "] " + taskName + " (" + deadline + ")";
    }
}
