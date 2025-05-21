package presentation.controllers;

import business.AdminManager;
import business.model.Slot;
import presentation.views.OccupancyChangeListener;

import java.sql.SQLException;

public class AdminController {
    private AdminManager adminManager;

    public AdminController() {
        adminManager = new AdminManager();
    }

    public String createSlot(Slot newSlot) {
        try {
            return adminManager.createSlot(newSlot);
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println("Error creating slot: " + e.getMessage());
            return "Error creating slot: " + e.getMessage();
        }
    }

    public boolean editSlot(Slot editSlot) {
        try {
            return adminManager.editSlot(editSlot);
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println("Error editing slot: " + e.getMessage());
            return false;
        }
    }

    public String deleteSlot(int idSlot) {
        try {
            return adminManager.deleteSlot(idSlot);
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println("Error deleting slot: " + e.getMessage());
            return "Error deleting slot";
        }
    }
    public int getNumByFloor (int floor) throws SQLException {
        return adminManager.getNumByFloor(floor);
    }
    public int getTotalSlots() throws SQLException {
        return adminManager.getTotalSlots();
    }
}
