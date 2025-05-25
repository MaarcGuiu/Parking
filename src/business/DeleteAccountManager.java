package business;

import business.model.User;
import persistence.UserSqlDao;

import java.sql.SQLException;

/**
 * The type Delete account manager.
 */
public class DeleteAccountManager {
    private UserSqlDao userSqlDao;

    /**
     * Instantiates a new Delete account manager.
     *
     * @throws SQLException the sql exception
     */
    public DeleteAccountManager() throws SQLException {
        this.userSqlDao = new UserSqlDao();
    }

    /**
     * Delete account string.
     *
     * @param emailOrName the email or name
     * @param password    the password
     * @return the string
     * @throws SQLException the sql exception
     */
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
