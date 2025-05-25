package business.exceptions;

public class RegisterException extends Throwable {
    public RegisterException(String s) {
        super(s);
    }

    public RegisterException(String message, Throwable cause) {
        super(message, cause);
    }
}
