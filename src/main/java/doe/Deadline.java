package doe;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task that must be completed by a stated deadline.
 * The deadline wording is kept as entered by the user.
 */
public class Deadline extends Task {
    /** The user-provided deadline note for this task. */
    private final LocalDateTime deadline;

    // Formatter to read the user input (e.g., 2026-10-15 1800)
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");
    // Formatter to display the date nicely (e.g., Oct 15 2026, 18:00)
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");

    /**
     * Creates an incomplete deadline task with its description and deadline note.
     *
     * @param taskName Description of the task.
     * @param deadline The deadline in dd-MM-yyyy HHmm format.
     * @throws DateTimeParseException If the data format is invalid
     */
    public Deadline(String taskName, String deadline) throws DateTimeParseException {
        super(taskName);
        this.deadline = LocalDateTime.parse(deadline.trim(), INPUT_FORMAT);
    }

    /**
     * Returns a string formatted for saving the deadline task to a file.
     *
     * @return File-formatted string representation of deadline task.
     */
    @Override
    public String toFileFormat() {
        return "DEADLINE|"  + super.toFileFormat() + "|" + deadline.format(INPUT_FORMAT);
    }

    /**
     * Returns this deadline in the format used when displaying a task list.
     *
     * @return String representation of deadline task.
     */
    @Override
    public String toString() {
        return "[d][" + getStatusIcon() + "] " + taskName + " (by: " + deadline.format(OUTPUT_FORMAT) + ")";
    }
}