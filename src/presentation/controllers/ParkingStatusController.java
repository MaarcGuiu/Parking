package presentation.controllers;

import business.ParkingStatusManager;
import business.model.CancelledReservation;
import business.model.Slot;

import java.sql.SQLException;
import java.util.List;

/**
 * The type Parking status controller.
 */
public class ParkingStatusController {
    private static ParkingStatusManager parkingStatusManager;

    /**
     * Instantiates a new Parking status controller.
     *
     * @throws SQLException the sql exception
     */
    public ParkingStatusController() throws SQLException {
        parkingStatusManager = new ParkingStatusManager();
    }

    /**
     * Gets all slots.
     *
     * @return the all slots
     * @throws SQLException the sql exception
     */
    public List<Slot> getAllSlots() throws SQLException {
        return parkingStatusManager.getAllSlots();
    }

    /**
     * Cancel slot boolean.
     *
     * @param slotId the slot id
     * @return the boolean
     * @throws SQLException the sql exception
     */
    public boolean cancelSlot(int slotId) throws SQLException {
        return parkingStatusManager.cancelSlot(slotId);
    }

    /**
     * Create cancelled reservation boolean.
     *
     * @param slotId       the slot id
     * @param userId       the user id
     * @param vehiclePlate the vehicle plate
     * @return the boolean
     * @throws SQLException the sql exception
     */
    public boolean createCancelledReservation(int slotId, int userId, String vehiclePlate) throws SQLException {
        return parkingStatusManager.createCancelledReservation(slotId, userId, vehiclePlate);
    }

    /**
     * Sets user new reservation slot.
     *
     * @param slotId       the slot id
     * @param vehiclePlate the vehicle plate
     * @return the user new reservation slot
     * @throws SQLException the sql exception
     */
    public int setUserNewReservationSlot(int slotId, String vehiclePlate) throws SQLException {
        return parkingStatusManager.setUserNewReservationSlot(slotId, vehiclePlate);
    }

    /**
     * Gets free unbooked slots.
     *
     * @return the free unbooked slots
     * @throws SQLException the sql exception
     */
    public static boolean getFreeUnbookedSlots() throws SQLException {
        return parkingStatusManager.getFreeUnbookedSlots();
    }

    /**
     * Gets slot.
     *
     * @param slotId the slot id
     * @return the slot
     * @throws SQLException the sql exception
     */
    public Slot getSlot(int slotId) throws SQLException {
        return parkingStatusManager.getSlot(slotId);
    }

    /**
     * Gets total slots.
     *
     * @return the total slots
     * @throws SQLException the sql exception
     */
    public int getTotalSlots() throws SQLException {
        return parkingStatusManager.getTotalSlots();
    }

    /**
     * Gets occupied slots count.
     *
     * @return the occupied slots count
     * @throws SQLException the sql exception
     */
    public int getOccupiedSlotsCount() throws SQLException {
        return parkingStatusManager.getOccupiedSlotsCount();
    }
}
