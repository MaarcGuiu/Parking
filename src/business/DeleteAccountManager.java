package business;

import business.model.User;
import persistence.UserSqlDao;

import java.sql.SQLException;

public class DeleteAccountManager {
    public String deleteAccount(String emailOrName, String password) {
        UserSqlDao dao = new UserSqlDao();
        try {
            User user = dao.getUser(emailOrName);
            dao.freeSlotsByOwnerId(user.getId());
            dao.deleteVehiclesByOwnerId(user.getId());
            dao.deleteAccount(emailOrName, password);
            return "success";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error en la base de datos.";
        }
    }
}
