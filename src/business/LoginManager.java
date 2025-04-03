package business;

import business.model.User;
import persistence.UserSqlDao;

import java.sql.SQLException;

public class LoginManager {
    public String login(String emailOrName, String password) {
        UserSqlDao dao = new UserSqlDao();
        try {
            return dao.login(emailOrName, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error en la base de datos.";
        }
    }

    public User getUser(String emailOrName) {
        UserSqlDao dao = new UserSqlDao();
        try {
            return dao.getUser(emailOrName);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
