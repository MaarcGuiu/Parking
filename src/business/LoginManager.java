package business;

import business.model.User;
import persistence.ConfigDao.ConfigJsonDao;
import persistence.UserSqlDao;

import java.sql.SQLException;

public class LoginManager {
    public String login(String emailOrName, String password) {
        UserSqlDao dao = new UserSqlDao();
        ConfigJsonDao configDao = new ConfigJsonDao();

        String adminPwd = configDao.loadAllConfig().getAdminPwd();
        try {
            return dao.login(emailOrName, password, adminPwd);
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
