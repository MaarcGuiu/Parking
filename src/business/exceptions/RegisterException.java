package business.exceptions;

/**
 * The type Register exception.
 */
public class RegisterException extends Throwable {
    /**
     * Instantiates a new Register exception.
     *
     * @param s the s
     */
    public RegisterException(String s) {
        super(s);
    }

    /**
     * Instantiates a new Register exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public RegisterException(String message, Throwable cause) {
        super(message, cause);
    }
}
