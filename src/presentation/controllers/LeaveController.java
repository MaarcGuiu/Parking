package presentation.controllers;


import business.LeaveManager;
import business.model.User;

import java.sql.SQLException;

public class LeaveController {
    private LeaveManager leaveManager;

    public LeaveController(User loggedUser) throws SQLException {
        leaveManager = new LeaveManager();
    }

    public String userPlate(User loggedUser, String plate) throws SQLException {
        return leaveManager.userPlate(loggedUser, plate);
    }

    public String isVehicleInside(String plate) throws SQLException {
        return leaveManager.isVehicleInside( plate);
    }

    public String updateSlot(String plate) throws SQLException {
        return leaveManager.updateSlot(plate);
    }

    public void registerExitLogs(String action, String plate, int slotId) throws SQLException {
        leaveManager.registerExitLogs(action, plate, slotId);
    }

    public int getSlotIdByPlate(String plate) throws SQLException {
        return leaveManager.getSlotIdByPlate(plate);
    }

    public boolean isValidPlateFormat(String plate) {
        return leaveManager.isValidPlateFormat(plate);
    }
}
