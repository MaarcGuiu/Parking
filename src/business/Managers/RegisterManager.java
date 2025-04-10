package business.Managers;

import persistence.SqlDaos.UserSqlDao;

import java.sql.SQLException;

public class    RegisterManager {

    private boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }

        int atPosition = email.indexOf('@');
        int dotPosition = email.indexOf('.', atPosition);
        return atPosition > 0 && dotPosition > atPosition + 1;
    }

    public String register(String name, String password, String email) {
        if (!isValidEmail(email)) {
            return "El correo electrónico no tiene un formato válido.";
        }
        UserSqlDao dao = new UserSqlDao();
        try {
            return dao.register(name, password, email);
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error en la base de datos.";
        }
    }
}
