package presentation.controllers;


import business.LeaveManager;
import business.model.User;

public class LeaveController {
    private LeaveManager leaveManager;

    public LeaveController(User loggedUser) {
        leaveManager = new LeaveManager();
    }

    public String userPlate(User loggedUser, String plate) {
        return leaveManager.userPlate(loggedUser, plate);
    }

    public String isVehicleInside(User loggedUser, String plate) {
        return leaveManager.isVehicleInside(loggedUser, plate);
    }

    public String updateSlot(String plate) {
        return leaveManager.updatePlate(plate);
    }
}
