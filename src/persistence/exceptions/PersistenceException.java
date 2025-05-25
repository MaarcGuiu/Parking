package persistence.exceptions;

/**
 * Abstract class that represents the persistance exception.
 */
public abstract class PersistenceException extends Exception {
  /**
   * Returns the error message of this exception.
   * @param message message of the exception
   * @return The error message
   */
  public PersistenceException(String message) {
    super(message);
  }
}

