package doe;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task for an event with user-provided timing details.
 * The timing wording is kept as entered by the user.
 */
public class Event extends Task {
    /** The user-provided timing note for this event. */
    private final LocalDateTime timing;

    // Formatter to read the user input (e.g., 2026-10-15 1800)
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");
    // Formatter to display the date nicely (e.g., Oct 15 2026, 18:00)
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");

    /**
     * Creates an incomplete event task with its description and deadline note.
     *
     * @param taskName Description of the task.
     * @param timing The timing in dd-MM-yyyy HHmm format.
     * @throws DateTimeParseException If the data format is invalid
     */
    public Event(String taskName, String timing) throws DateTimeParseException {
        super(taskName);
        this.timing = LocalDateTime.parse(timing.trim(), INPUT_FORMAT);
    }

    /**
     * Returns a string formatted for saving the event task to a file.
     *
     * @return File-formatted string representation of event task.
     */
    @Override
    public String toFileFormat() {
        return "EVENT|"  + super.toFileFormat() + "|" + timing.format(INPUT_FORMAT);
    }

    /**
     * Returns this event in the format used when displaying a task list.
     *
     * @return String representation of event task.
     */
    @Override
    public String toString() {
        return "[e][" + getStatusIcon() + "] " + taskName + " (at: " + timing.format(OUTPUT_FORMAT) + ")";
    }
}
