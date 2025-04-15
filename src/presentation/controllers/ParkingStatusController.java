package presentation.controllers;

import business.ParkingStatusManager;
import business.model.Slot;

import java.util.List;

public class ParkingStatusController {
    private ParkingStatusManager parkingStatusManager;

    public ParkingStatusController() {
        parkingStatusManager = new ParkingStatusManager();
    }

    public List<Slot> getAllSlots() {
        return parkingStatusManager.getAllSlots();
    }

    public boolean cancelSlot(int slotId) {
        return parkingStatusManager.cancelSlot(slotId);
    }
}
