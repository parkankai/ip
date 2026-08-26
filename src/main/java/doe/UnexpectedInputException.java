package doe;


/**
 * Signals that the user entered a command or value that is not valid in the current prompt.
 */
public class UnexpectedInputException extends Exception {

    /** Creates an exception containing the existing user-facing error message. */
    public UnexpectedInputException(String message) {
        super(message);
    }
}
