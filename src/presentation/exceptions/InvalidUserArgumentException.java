package presentation.exceptions;

/**
 * Class representing the illegal arfument extending from BuissinesException.
 */
public class InvalidUserArgumentException extends PresentationException {
  /**
   * Returns the error message of this exception.
   * @param message String that has the info of the exeption
   * @return The error message
   */
  public InvalidUserArgumentException(String message) {
    super(message);
  }
}
