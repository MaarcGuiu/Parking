package presentation.controllers;

import business.AdminManager;
import business.model.Slot;
import presentation.views.OccupancyChangeListener;

import java.sql.SQLException;

/**
 * The type Admin controller.
 */
public class AdminController {
    private AdminManager adminManager;

    /**
     * Instantiates a new Admin controller.
     *
     * @throws SQLException           the sql exception
     * @throws ClassNotFoundException the class not found exception
     */
    public AdminController() throws SQLException, ClassNotFoundException {
        adminManager = new AdminManager();
    }

    /**
     * Create slot string.
     *
     * @param newSlot the new slot
     * @return the string
     * @throws IllegalArgumentException the illegal argument exception
     * @throws SQLException             the sql exception
     */
    public String createSlot(Slot newSlot) throws IllegalArgumentException, SQLException {
        return adminManager.createSlot(newSlot);
    }

    /**
     * Edit slot boolean.
     *
     * @param editSlot the edit slot
     * @return the boolean
     * @throws IllegalArgumentException the illegal argument exception
     * @throws SQLException             the sql exception
     */
    public boolean editSlot(Slot editSlot) throws IllegalArgumentException, SQLException{
        return adminManager.editSlot(editSlot);
    }

    /**
     * Delete slot string.
     *
     * @param idSlot the id slot
     * @return the string
     * @throws IllegalArgumentException the illegal argument exception
     * @throws SQLException             the sql exception
     */
    public String deleteSlot(int idSlot) throws IllegalArgumentException, SQLException{
        return adminManager.deleteSlot(idSlot);
    }

    /**
     * Gets num by floor.
     *
     * @param floor the floor
     * @return the num by floor
     * @throws SQLException the sql exception
     */
    public int getNumByFloor (int floor) throws SQLException {
        return adminManager.getNumByFloor(floor);
    }

    /**
     * Gets total slots.
     *
     * @return the total slots
     * @throws SQLException the sql exception
     */
    public int getTotalSlots() throws SQLException {
        return adminManager.getTotalSlots();
    }
}
