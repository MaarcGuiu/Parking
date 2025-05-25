package presentation.controllers;

import business.AdminManager;
import business.model.Slot;
import presentation.views.OccupancyChangeListener;

import java.sql.SQLException;

public class AdminController {
    private AdminManager adminManager;

    public AdminController() throws SQLException, ClassNotFoundException {
        adminManager = new AdminManager();
    }

    public String createSlot(Slot newSlot) throws IllegalArgumentException, SQLException {
        return adminManager.createSlot(newSlot);
    }

    public boolean editSlot(Slot editSlot) throws IllegalArgumentException, SQLException{
        return adminManager.editSlot(editSlot);
    }

    public String deleteSlot(int idSlot) throws IllegalArgumentException, SQLException{
        return adminManager.deleteSlot(idSlot);
    }

    public int getNumByFloor (int floor) throws SQLException {
        return adminManager.getNumByFloor(floor);
    }
    public int getTotalSlots() throws SQLException {
        return adminManager.getTotalSlots();
    }
}
