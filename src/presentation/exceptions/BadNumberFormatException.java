package presentation.exceptions;

/**
 * Abstract class that represents bad format number extends of Presentation exception.
 */
public abstract class BadNumberFormatException extends PresentationException {
    /**
     * Returns the error message of this exception.
     *
     * @param message message of the exception
     * @return The error message
     */
    public BadNumberFormatException(String message) {
        super(message);
    }
}