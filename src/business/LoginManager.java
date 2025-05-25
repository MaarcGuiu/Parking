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

public class LoginManager {
    private UserSqlDao userDao;

    public LoginManager() throws SQLException {
        this.userDao = new UserSqlDao();
    }

    public String login(String emailOrName, String password) throws SQLException {
        ConfigJsonDao configDao = new ConfigJsonDao();

        String adminPwd = configDao.loadAllConfig().getAdminPwd();
        return userDao.login(emailOrName, password, adminPwd);
    }

    public User getUser(String emailOrName) throws SQLException {
        return userDao.getUser(emailOrName);
    }

    public List<CancelledReservation> getCancelledReservationsByUserId(int userId) throws SQLException {
        CancelledReservationSqlDao cancelledDao = new CancelledReservationSqlDao();
        return cancelledDao.getCancelledReservationsByUserId(userId);
    }

    public void deleteCancelledReservationById(int id) throws SQLException {
        CancelledReservationSqlDao cancelledDao = new CancelledReservationSqlDao();
        cancelledDao.deleteCancelledReservationsById(id);
    }

    public Slot getSlotByPlate(String vehiclePlate) throws SQLException {
        SlotSqlDao slotDao = new SlotSqlDao();
        return slotDao.getSlotByPlate(vehiclePlate);
    }
}
