package presentation.controllers;

import business.EnterManager;
import business.LeaveManager;
import business.model.User;

public class EnterController {
    private EnterManager enterManager;

    public EnterController(User loggedUser) {
        enterManager = new EnterManager();
    }

    public String registeredVehicle(String plate) {
        return enterManager.registeredVehicle(plate);
    }

    public String isBooked(String plate) {
        return enterManager.isBooked(plate);
    }

    public String placesAvailable(String plate, String vehicle) {
        return enterManager.placesAvailable(plate, vehicle);
    }
}
