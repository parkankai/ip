/**
 * Represents a task for an event with user-provided timing details.
 * The timing wording is kept as entered by the user.
 */
public class Event extends Task {
    /** The user-provided timing note for this event. */
    private final String timing;

    /** Creates an incomplete event task with its description and timing note. */
    public Event(String description, String timing) {
        super(description);
        this.timing = timing;
    }

    /** Returns this event in the format used when displaying a task list. */
    @Override
    public String toString() {
        return "[e][" + getStatusIcon() + "] " + description + " (" + timing + ")";
    }
}
