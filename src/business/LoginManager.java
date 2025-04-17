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

    public List<CancelledReservation> getCancelledReservationsByUserId(int userId) {
        CancelledReservationSqlDao cancelledDao = new CancelledReservationSqlDao();
        try {
            return cancelledDao.getCancelledReservationsByUserId(userId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteCancelledReservationById(int id) {
        CancelledReservationSqlDao cancelledDao = new CancelledReservationSqlDao();
        try {
            cancelledDao.deleteCancelledReservationsById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Slot getSlotByPlate(String vehiclePlate) {
        SlotSqlDao slotDao = new SlotSqlDao();
        try {
            return slotDao.getSlotByPlate(vehiclePlate);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
