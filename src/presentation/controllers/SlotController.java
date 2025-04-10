package presentation.controllers;

import business.SlotManager;
import business.model.Slot;

import java.sql.SQLException;

public class SlotController {
    private SlotManager slotManager;

    public SlotController() {
        slotManager = new SlotManager();
    }

    public boolean createSlot(Slot newSlot) {
        try {
            return slotManager.createSlot(newSlot);
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println("Error creating slot: " + e.getMessage());
            return false;
        }
    }

    public boolean editSlot(Slot editSlot) {
        try {
            return slotManager.editSlot(editSlot);
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println("Error editing slot: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteSlot(int idSlot) {
        try {
            return slotManager.deleteSlot(idSlot);
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println("Error deleting slot: " + e.getMessage());
            return false;
        }
    }
}
