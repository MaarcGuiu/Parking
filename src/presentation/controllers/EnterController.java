package presentation.controllers;

import business.EnterManager;
import business.LeaveManager;
import business.model.User;

public class EnterController {
    private EnterManager enterManager;

    public EnterController(User loggedUser) {
        enterManager = new EnterManager();
    }

    public String registeredVehicle(User loggedUser, String plate) {
        return enterManager.registeredVehicle(loggedUser, plate);
    }

    public String isBooked(User loggedUser, String plate) {
        return enterManager.isBooked(loggedUser, plate);
    }

//    public String typeVehicle(User loggedUser, String plate, String vehicle) {
//        return enterManager.typeVehicle(loggedUser, plate, vehicle);
//    }

    public String placesAvailable(User loggedUser, String plate, String vehicle) {
        return enterManager.placesAvailable(loggedUser, plate, vehicle);
    }

    public boolean sameTypeVehicle(User loggedUser, String plate, String vehicle) {
        return enterManager.sameTypeVehicle(loggedUser, plate, vehicle);
    }
}
