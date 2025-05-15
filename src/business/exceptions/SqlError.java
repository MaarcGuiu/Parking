package business.exceptions;

/**
 * Class representing the sql error extending from BuissinesException.
 */
public class SqlError extends BusinessException {
  /**
   * Returns the error message of this exception.
   * @param message String that has the info of the exeption
   * @return The error message
   */
  public SqlError(String message) {
    super(message);
  }
}
