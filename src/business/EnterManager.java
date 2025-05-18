package business;

import business.model.User;
import persistence.UserSqlDao;

import java.sql.SQLException;

public class EnterManager {

    public String registeredVehicle(String plate) throws SQLException {
        UserSqlDao dao = new UserSqlDao();
        return dao.registeredVehicle(plate);
    }

    public String isBooked(String plate) throws SQLException {
        UserSqlDao dao = new UserSqlDao();
        return dao.isBooked(plate);
    }

    public String placesAvailable(String plate, String vehicle) throws SQLException {
        UserSqlDao dao = new UserSqlDao();
        if (dao.vehicleExists(plate)) {
            if (!dao.sameTypeVehicle(plate, vehicle)) {
                return "notEqual";
            }
        }
        return dao.placesAvailable(plate, vehicle);
    }

    public String registerVehicle(User loggedUser, String plate, String vehicle) throws SQLException {
        UserSqlDao dao = new UserSqlDao();
        return dao.registerVehicle(loggedUser, plate, vehicle);
    }

    public boolean isValidPlateFormat(String plate) {
        return plate != null && plate.matches("^[A-Z]{3}\\d{3}$");
    }

    public void registerEntryLogs(String action, String plate, int slotId) throws SQLException {
        UserSqlDao dao = new UserSqlDao();
        dao.registerEntryExitLogs(action, plate, slotId);
    }

    public int getSlotIdByPlate(String plate) throws SQLException {
        UserSqlDao dao = new UserSqlDao();
        return dao.getSlotIdByPlate(plate);
    }

    public boolean isUserPlate(User loggedUser, String plate) throws SQLException {
        UserSqlDao dao = new UserSqlDao();
        return dao.isUserPlate(loggedUser, plate);
    }

    public boolean vehicleExists(String plate) throws SQLException {
        UserSqlDao dao = new UserSqlDao();
        return dao.vehicleExists(plate);
    }
}
