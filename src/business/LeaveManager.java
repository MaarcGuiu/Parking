package business;

import business.model.User;
import persistence.UserSqlDao;

import java.sql.SQLException;

/**
 * The type Leave manager.
 */
public class LeaveManager {
    private UserSqlDao userDao;

    /**
     * Instantiates a new Leave manager.
     *
     * @throws SQLException the sql exception
     */
    public LeaveManager() throws SQLException {
        this.userDao = new UserSqlDao();
    }

    /**
     * User plate string.
     *
     * @param loggedUser the logged user
     * @param plate      the plate
     * @return the string
     * @throws SQLException the sql exception
     */
    public String userPlate(User loggedUser, String plate) throws SQLException {
        return userDao.userPlate(loggedUser, plate);
    }

    /**
     * Is vehicle inside string.
     *
     * @param plate the plate
     * @return the string
     * @throws SQLException the sql exception
     */
    public String isVehicleInside(String plate) throws SQLException {
        if (userDao.isVehicleInside(plate)) {
            return "success";
        } else {
            return "The vehicle is not in the parking lot.";
        }
    }

    /**
     * Update slot string.
     *
     * @param plate the plate
     * @return the string
     * @throws SQLException the sql exception
     */
    public String updateSlot(String plate) throws SQLException {
        return userDao.updateSlot(plate);
    }

    /**
     * Register exit logs.
     *
     * @param action the action
     * @param plate  the plate
     * @param slotId the slot id
     * @throws SQLException the sql exception
     */
    public void registerExitLogs(String action, String plate, int slotId) throws SQLException {
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
     * Is valid plate format boolean.
     *
     * @param plate the plate
     * @return the boolean
     */
    public boolean isValidPlateFormat(String plate) {
        return plate != null && plate.matches("^[A-Z]{3}\\d{3}$");
    }
}
