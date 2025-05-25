package business;

import business.exceptions.RegisterException;
import persistence.UserSqlDao;
import presentation.controllers.RegisterController;

import java.sql.SQLException;

/**
 * The type Register manager.
 */
public class RegisterManager {
    private UserSqlDao userDao;

    /**
     * Instantiates a new Register manager.
     *
     * @throws SQLException the sql exception
     */
    public RegisterManager() throws SQLException {
        userDao = new UserSqlDao();
    }

    private boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }

        int atPosition = email.indexOf('@');
        int dotPosition = email.indexOf('.', atPosition);
        return atPosition > 0 && dotPosition > atPosition + 1;
    }

    /**
     * Register string.
     *
     * @param name     the name
     * @param password the password
     * @param email    the email
     * @return the string
     * @throws RegisterException the register exception
     */
    public String register(String name, String password, String email) throws RegisterException {
        if (!isValidEmail(email)) {
            throw new RegisterException("El correo electrónico no tiene un formato válido.");
        }

        try {
            return userDao.register(name, password, email);
        } catch (SQLException e) {
            throw new RegisterException("Error en la base de datos al registrar el usuario.", e);
        }
    }
}
