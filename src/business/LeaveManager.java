package business;

import business.model.User;
import persistence.UserSqlDao;

import java.sql.SQLException;

public class LeaveManager {

    public String userPlate(User loggedUser, String plate) throws SQLException {
        UserSqlDao dao = new UserSqlDao();
        return dao.userPlate(loggedUser, plate);
    }

    public String isVehicleInside(String plate) throws SQLException {
        UserSqlDao dao = new UserSqlDao();
        if (dao.isVehicleInside(plate)) {
            return "success";
        } else {
            return "The vehicle is not in the parking lot.";
        }
    }

    public String updateSlot(String plate) throws SQLException {
        UserSqlDao dao = new UserSqlDao();
        return dao.updateSlot(plate);
    }

    public void registerExitLogs(String action, String plate, int slotId) throws SQLException {
        UserSqlDao dao = new UserSqlDao();
        dao.registerEntryExitLogs(action, plate, slotId);
    }

    public int getSlotIdByPlate(String plate) throws SQLException {
        UserSqlDao dao = new UserSqlDao();
        return dao.getSlotIdByPlate(plate);
    }

    public boolean isValidPlateFormat(String plate) {
        return plate != null && plate.matches("^[A-Z]{3}\\d{3}$");
    }
}
