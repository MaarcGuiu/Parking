package presentation.controllers;

import business.ParkingStatusManager;
import business.model.CancelledReservation;
import business.model.Slot;

import java.sql.SQLException;
import java.util.List;

public class ParkingStatusController {
    private static ParkingStatusManager parkingStatusManager;

    public ParkingStatusController() throws SQLException {
        parkingStatusManager = new ParkingStatusManager();
    }

    public List<Slot> getAllSlots() throws SQLException {
        return parkingStatusManager.getAllSlots();
    }

    public boolean cancelSlot(int slotId) throws SQLException {
        return parkingStatusManager.cancelSlot(slotId);
    }

    public boolean createCancelledReservation(int slotId, int userId, String vehiclePlate) throws SQLException {
        return parkingStatusManager.createCancelledReservation(slotId, userId, vehiclePlate);
    }

    public int setUserNewReservationSlot(int slotId, String vehiclePlate) throws SQLException {
        return parkingStatusManager.setUserNewReservationSlot(slotId, vehiclePlate);
    }

    public static boolean getFreeUnbookedSlots() throws SQLException {
        return parkingStatusManager.getFreeUnbookedSlots();
    }

    public Slot getSlot(int slotId) throws SQLException {
        return parkingStatusManager.getSlot(slotId);
    }
}
