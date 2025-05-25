package presentation.controllers;

import business.EnterManager;
import business.LeaveManager;
import business.model.User;

import java.sql.SQLException;

/**
 * The type Enter controller.
 */
public class EnterController {
    private EnterManager enterManager;

    /**
     * Instantiates a new Enter controller.
     *
     * @param loggedUser the logged user
     * @throws SQLException the sql exception
     */
    public EnterController(User loggedUser) throws SQLException {
        enterManager = new EnterManager();
    }

    /**
     * Registered vehicle string.
     *
     * @param plate the plate
     * @return the string
     * @throws SQLException the sql exception
     */
    public String registeredVehicle(String plate) throws SQLException{
        return enterManager.registeredVehicle(plate);
    }

    /**
     * Is booked string.
     *
     * @param plate the plate
     * @return the string
     * @throws SQLException the sql exception
     */
    public String isBooked(String plate) throws SQLException{
        return enterManager.isBooked(plate);
    }

    /**
     * Places available string.
     *
     * @param plate   the plate
     * @param vehicle the vehicle
     * @return the string
     * @throws SQLException the sql exception
     */
    public String placesAvailable(String plate, String vehicle) throws SQLException{
        return enterManager.placesAvailable(plate, vehicle);
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
    public String registerVehicle(User loggedUser, String plate, String vehicle) throws SQLException{
        return enterManager.registerVehicle(loggedUser, plate, vehicle);
    }

    /**
     * Register entry logs.
     *
     * @param action the action
     * @param plate  the plate
     * @param slotId the slot id
     * @throws SQLException the sql exception
     */
    public void registerEntryLogs(String action, String plate, int slotId) throws SQLException{
        enterManager.registerEntryLogs(action, plate, slotId);
    }

    /**
     * Gets slot id by plate.
     *
     * @param plate the plate
     * @return the slot id by plate
     * @throws SQLException the sql exception
     */
    public int getSlotIdByPlate(String plate) throws SQLException {
        return enterManager.getSlotIdByPlate(plate);
    }

    /**
     * Is valid plate format boolean.
     *
     * @param plate the plate
     * @return the boolean
     */
    public boolean isValidPlateFormat(String plate) {
        return enterManager.isValidPlateFormat(plate);
    }

    /**
     * Is user plate boolean.
     *
     * @param loggedUser the logged user
     * @param plate      the plate
     * @return the boolean
     * @throws SQLException the sql exception
     */
    public boolean isUserPlate(User loggedUser, String plate) throws SQLException{
        return enterManager.isUserPlate(loggedUser, plate);
    }

    /**
     * Vehicle exists boolean.
     *
     * @param plate the plate
     * @return the boolean
     * @throws SQLException the sql exception
     */
    public boolean vehicleExists(String plate) throws SQLException {
        return enterManager.vehicleExists(plate);
    }
}
