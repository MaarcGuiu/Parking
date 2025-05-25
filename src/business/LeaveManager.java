package business;

import business.model.User;
import persistence.UserSqlDao;

import java.sql.SQLException;

public class LeaveManager {
    private UserSqlDao userDao;

    public LeaveManager() throws SQLException {
        this.userDao = new UserSqlDao();
    }

    public String userPlate(User loggedUser, String plate) throws SQLException {
        return userDao.userPlate(loggedUser, plate);
    }

    public String isVehicleInside(String plate) throws SQLException {
        if (userDao.isVehicleInside(plate)) {
            return "success";
        } else {
            return "The vehicle is not in the parking lot.";
        }
    }

    public String updateSlot(String plate) throws SQLException {
        return userDao.updateSlot(plate);
    }

    public void registerExitLogs(String action, String plate, int slotId) throws SQLException {
        userDao.registerEntryExitLogs(action, plate, slotId);
    }

    public int getSlotIdByPlate(String plate) throws SQLException {
        return userDao.getSlotIdByPlate(plate);
    }

    public boolean isValidPlateFormat(String plate) {
        return plate != null && plate.matches("^[A-Z]{3}\\d{3}$");
    }
}
