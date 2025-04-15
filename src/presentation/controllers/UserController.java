package presentation.controllers;

import business.UserManager;
import business.model.Slot;

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

}
