package business;

import business.model.User;
import persistence.SlotSqlDao;
import persistence.UserSqlDao;

import java.sql.SQLException;

/**
 * The type Enter manager.
 */
public class EnterManager {
    private UserSqlDao userDao;

    /**
     * Instantiates a new Enter manager.
     *
     * @throws SQLException the sql exception
     */
    public EnterManager() throws SQLException {
        this.userDao = new UserSqlDao();
    }

    /**
     * Registered vehicle string.
     *
     * @param plate the plate
     * @return the string
     * @throws SQLException the sql exception
     */
    public String registeredVehicle(String plate) throws SQLException {
        return userDao.registeredVehicle(plate);
    }

    /**
     * Is booked string.
     *
     * @param plate the plate
     * @return the string
     * @throws SQLException the sql exception
     */
    public String isBooked(String plate) throws SQLException {
        return userDao.isBooked(plate);
    }

    /**
     * Places available string.
     *
     * @param plate   the plate
     * @param vehicle the vehicle
     * @return the string
     * @throws SQLException the sql exception
     */
    public String placesAvailable(String plate, String vehicle) throws SQLException {
        if (userDao.vehicleExists(plate)) {
            if (!userDao.sameTypeVehicle(plate, vehicle)) {
                return "notEqual";
            }
        }
        return userDao.placesAvailable(plate, vehicle);
    }

    /**
     * Register vehicle string.
     *
     * @param loggedUser the logged user
     * @param plate      the plate
     * @param vehicle    the vehicle
     * @return the string
     * @throws SQLException the sql exception
     */
    public String registerVehicle(User loggedUser, String plate, String vehicle) throws SQLException {
        return userDao.registerVehicle(loggedUser, plate, vehicle);
    }

    /**
     * Is valid plate format boolean.
     *
     * @param plate the plate
     * @return the boolean
     */
    public boolean isValidPlateFormat(String plate) {
        return plate != null && plate.matches("^[A-Z]{3}\\d{3}$");
    }

    /**
     * Register entry logs.
     *
     * @param action the action
     * @param plate  the plate
     * @param slotId the slot id
     * @throws SQLException the sql exception
     */
    public void registerEntryLogs(String action, String plate, int slotId) throws SQLException {
        userDao.registerEntryExitLogs(action, plate, slotId);
    }

    /**
     * Gets slot id by plate.
     *
     * @param plate the plate
     * @return the slot id by plate
     * @throws SQLException the sql exception
     */
    public int getSlotIdByPlate(String plate) throws SQLException {
        return userDao.getSlotIdByPlate(plate);
    }

    /**
     * Is user plate boolean.
     *
     * @param loggedUser the logged user
     * @param plate      the plate
     * @return the boolean
     * @throws SQLException the sql exception
     */
    public boolean isUserPlate(User loggedUser, String plate) throws SQLException {
        return userDao.isUserPlate(loggedUser, plate);
    }

    /**
     * Vehicle exists boolean.
     *
     * @param plate the plate
     * @return the boolean
     * @throws SQLException the sql exception
     */
    public boolean vehicleExists(String plate) throws SQLException {
        return userDao.vehicleExists(plate);
    }
}
