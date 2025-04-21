package presentation.controllers;

import business.UserManager;
import business.model.Slot;
import business.model.Vehicle;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserController {
    private UserManager userManager;
    public UserController () {
    }

    public void userEntry () {

    }
    public boolean checkUserBooking (String vehiclePlate) throws SQLException {
        return userManager.checkUserBooking(vehiclePlate);
    }
    //DEVUELVE EL SLOT YA ACTUALIZADO, BOOKED = 0 Y OCCUPIED ES = 1 ya que pasa a estar ocupado.
    public Slot getSlotBooked(String vehiclePlate) throws SQLException {
        return userManager.getSlotBooked(vehiclePlate);
    }
    //Todos los slots libres
    public ArrayList<Slot> getFreeUnbookedSlots() throws SQLException {
        return userManager.getFreeUnbookedSlots();
    }
    //True si el slot
    public boolean isYourVehicleCorrect(Slot slot, Vehicle vehicle){
        return userManager.isYourVehicleCorrect(slot,vehicle);
    }
    // ENTRIES:
    public void userEntryIfbooked(String plate) throws SQLException {
        userManager.userEntryIfbooked(plate);
    }
    public boolean userEntryNotbooked(String plate, String vehicle_type) throws SQLException {
        return userManager.userEntryNotbooked(plate,vehicle_type);
    }
    //EXIT:
    public void userExit (String vehicle_plate) throws SQLException {
        userManager.userExit(vehicle_plate);
    }
    // Te da todos los vehiculos que tienen reserva esto es util para el panel
    // SOLO quedaría añadir el momento que se hace la reserva con variable tipo Date
    public ArrayList<Vehicle> getPanelBookings(int userId) throws SQLException {
        return userManager.getPanelBookings(userId);
    }
}
