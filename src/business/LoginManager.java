package business;

import business.model.CancelledReservation;
import business.model.Slot;
import business.model.User;
import persistence.CancelledReservationSqlDao;
import persistence.ConfigDao.ConfigJsonDao;
import persistence.SlotSqlDao;
import persistence.UserSqlDao;

import java.sql.SQLException;
import java.util.List;

/**
 * The type Login manager.
 */
public class LoginManager {
    private UserSqlDao userDao;

    /**
     * Instantiates a new Login manager.
     *
     * @throws SQLException the sql exception
     */
    public LoginManager() throws SQLException {
        this.userDao = new UserSqlDao();
    }

    /**
     * Login string.
     *
     * @param emailOrName the email or name
     * @param password    the password
     * @return the string
     * @throws SQLException the sql exception
     */
    public String login(String emailOrName, String password) throws SQLException {
        ConfigJsonDao configDao = new ConfigJsonDao();

        String adminPwd = configDao.loadAllConfig().getAdminPwd();
        return userDao.login(emailOrName, password, adminPwd);
    }

    /**
     * Gets user.
     *
     * @param emailOrName the email or name
     * @return the user
     * @throws SQLException the sql exception
     */
    public User getUser(String emailOrName) throws SQLException {
        return userDao.getUser(emailOrName);
    }

    /**
     * Gets cancelled reservations by user id.
     *
     * @param userId the user id
     * @return the cancelled reservations by user id
     * @throws SQLException the sql exception
     */
    public List<CancelledReservation> getCancelledReservationsByUserId(int userId) throws SQLException {
        CancelledReservationSqlDao cancelledDao = new CancelledReservationSqlDao();
        return cancelledDao.getCancelledReservationsByUserId(userId);
    }

    /**
     * Delete cancelled reservation by id.
     *
     * @param id the id
     * @throws SQLException the sql exception
     */
    public void deleteCancelledReservationById(int id) throws SQLException {
        CancelledReservationSqlDao cancelledDao = new CancelledReservationSqlDao();
        cancelledDao.deleteCancelledReservationsById(id);
    }

    /**
     * Gets slot by plate.
     *
     * @param vehiclePlate the vehicle plate
     * @return the slot by plate
     * @throws SQLException the sql exception
     */
    public Slot getSlotByPlate(String vehiclePlate) throws SQLException {
        SlotSqlDao slotDao = new SlotSqlDao();
        return slotDao.getSlotByPlate(vehiclePlate);
    }
}
