package business.exceptions;

/**
 * Abstract class that represents the business exception.
 */
public abstract class BusinessException extends Exception {
    /**
     * Returns the error message of this exception.
     *
     * @param message message of the exception
     * @return The error message
     */
    public BusinessException(String message) {
        super(message);
    }
}
