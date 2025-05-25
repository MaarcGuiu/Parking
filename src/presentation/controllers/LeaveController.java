package presentation.controllers;


import business.LeaveManager;
import business.model.User;

import java.sql.SQLException;

/**
 * The type Leave controller.
 */
public class LeaveController {
    private LeaveManager leaveManager;

    /**
     * Instantiates a new Leave controller.
     *
     * @param loggedUser the logged user
     * @throws SQLException the sql exception
     */
    public LeaveController(User loggedUser) throws SQLException {
        leaveManager = new LeaveManager();
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
        return leaveManager.userPlate(loggedUser, plate);
    }

    /**
     * Is vehicle inside string.
     *
     * @param plate the plate
     * @return the string
     * @throws SQLException the sql exception
     */
    public String isVehicleInside(String plate) throws SQLException {
        return leaveManager.isVehicleInside( plate);
    }

    /**
     * Update slot string.
     *
     * @param plate the plate
     * @return the string
     * @throws SQLException the sql exception
     */
    public String updateSlot(String plate) throws SQLException {
        return leaveManager.updateSlot(plate);
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
        leaveManager.registerExitLogs(action, plate, slotId);
    }

    /**
     * Gets slot id by plate.
     *
     * @param plate the plate
     * @return the slot id by plate
     * @throws SQLException the sql exception
     */
    public int getSlotIdByPlate(String plate) throws SQLException {
        return leaveManager.getSlotIdByPlate(plate);
    }

    /**
     * Is valid plate format boolean.
     *
     * @param plate the plate
     * @return the boolean
     */
    public boolean isValidPlateFormat(String plate) {
        return leaveManager.isValidPlateFormat(plate);
    }
}
