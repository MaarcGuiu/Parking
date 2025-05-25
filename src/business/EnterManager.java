package business;

import business.model.User;
import persistence.SlotSqlDao;
import persistence.UserSqlDao;

import java.sql.SQLException;

public class EnterManager {
    private UserSqlDao userDao;

    public EnterManager() throws SQLException {
        this.userDao = new UserSqlDao();
    }
    public String registeredVehicle(String plate) throws SQLException {
        return userDao.registeredVehicle(plate);
    }

    public String isBooked(String plate) throws SQLException {
        return userDao.isBooked(plate);
    }

    public String placesAvailable(String plate, String vehicle) throws SQLException {
        if (userDao.vehicleExists(plate)) {
            if (!userDao.sameTypeVehicle(plate, vehicle)) {
                return "notEqual";
            }
        }
        return userDao.placesAvailable(plate, vehicle);
    }

    public String registerVehicle(User loggedUser, String plate, String vehicle) throws SQLException {
        return userDao.registerVehicle(loggedUser, plate, vehicle);
    }

    public boolean isValidPlateFormat(String plate) {
        return plate != null && plate.matches("^[A-Z]{3}\\d{3}$");
    }

    public void registerEntryLogs(String action, String plate, int slotId) throws SQLException {
        userDao.registerEntryExitLogs(action, plate, slotId);
    }

    public int getSlotIdByPlate(String plate) throws SQLException {
        return userDao.getSlotIdByPlate(plate);
    }

    public boolean isUserPlate(User loggedUser, String plate) throws SQLException {
        return userDao.isUserPlate(loggedUser, plate);
    }

    public boolean vehicleExists(String plate) throws SQLException {
        return userDao.vehicleExists(plate);
    }
}
