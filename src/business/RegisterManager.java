package business;

import business.exceptions.RegisterException;
import persistence.UserSqlDao;
import presentation.controllers.RegisterController;

import java.sql.SQLException;

public class RegisterManager {
    private UserSqlDao userDao;

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
