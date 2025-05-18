package presentation.controllers;

import business.EnterManager;
import business.LeaveManager;
import business.model.User;

import java.sql.SQLException;

public class EnterController {
    private EnterManager enterManager;

    public EnterController(User loggedUser) {
        enterManager = new EnterManager();
    }

    public String registeredVehicle(String plate) throws SQLException{
        return enterManager.registeredVehicle(plate);
    }

    public String isBooked(String plate) throws SQLException{
        return enterManager.isBooked(plate);
    }

    public String placesAvailable(String plate, String vehicle) throws SQLException{
        return enterManager.placesAvailable(plate, vehicle);
    }

    public String registerVehicle(User loggedUser, String plate, String vehicle) throws SQLException{
        return enterManager.registerVehicle(loggedUser, plate, vehicle);
    }

    public void registerEntryLogs(String action, String plate, int slotId) throws SQLException{
        enterManager.registerEntryLogs(action, plate, slotId);
    }

    public int getSlotIdByPlate(String plate) throws SQLException {
        return enterManager.getSlotIdByPlate(plate);
    }

    public boolean isValidPlateFormat(String plate) {
        return enterManager.isValidPlateFormat(plate);
    }

    public boolean isUserPlate(User loggedUser, String plate) throws SQLException{
        return enterManager.isUserPlate(loggedUser, plate);
    }

    public boolean vehicleExists(String plate) throws SQLException {
        return enterManager.vehicleExists(plate);
    }
}
