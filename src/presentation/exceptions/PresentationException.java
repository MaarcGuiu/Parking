package presentation.exceptions;

/**
 * Abstract class that represents the Presentation exception.
 */
public abstract class PresentationException extends Exception {
    /**
     * Returns the error message of this exception.
     * @param message message of the exception
     * @return The error message
     */
    public PresentationException(String message) {
        super(message);
    }
}