package business;

import business.model.User;
import persistence.UserSqlDao;

import java.sql.SQLException;

public class DeleteAccountManager {
    private UserSqlDao userSqlDao;

    public DeleteAccountManager() throws SQLException {
        this.userSqlDao = new UserSqlDao();
    }

    public String deleteAccount(String emailOrName, String password) throws SQLException {
        User user = userSqlDao.getUser(emailOrName);

        if (user == null) {
            throw new IllegalArgumentException("User not found.");
        }

        userSqlDao.freeSlotsByOwnerId(user.getId());
        userSqlDao.deleteVehiclesByOwnerId(user.getId());
        userSqlDao.deleteAccount(emailOrName, password);

        return "success";
    }
}
