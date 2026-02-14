package lime;

/**
 * Exception type for user-facing command errors.
 */
public class LimeException extends Exception {
    /**
     * Creates a new LimeException with a message.
     *
     * @param message error message
     */
    public LimeException(String message) {
        super(message);
    }
}
