package persistence.exceptions;

/**
 * Class representing the sql error extending from PersistanceException.
 */
public class SqlError extends PersistenceException {
    /**
     * Returns the error message of this exception.
     * @param message String that has the info of the exeption
     * @return The error message
     */
    public SqlError(String message) {
        super(message);
    }
}
