package presentation.controllers;

import business.UserManager;
import business.model.Slot;
import business.model.Vehicle;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserController {
    private UserManager userManager;

    public UserController () throws SQLException {
        this.userManager = new UserManager();
    }

    public void userEntry () {

    }

    public boolean checkUserBooking (String vehiclePlate) throws SQLException {
        return userManager.checkUserBooking(vehiclePlate);
    }

    //Todos los slots libres
    public ArrayList<Slot> getFreeUnbookedSlots() throws SQLException {
        return userManager.getFreeUnbookedSlots();
    }

    // Te da todos los vehiculos que tienen reserva esto es util para el panel
    // SOLO quedaría añadir el momento que se hace la reserva con variable tipo Date
    public ArrayList<Vehicle> getPanelBookings(int userId) throws SQLException {
        return userManager.getPanelBookings(userId);
    }
    
    // Crear booked
    public void updateTheSlotBooked(String plate, int idSlot) throws SQLException {
        userManager.updateTheSlotBooked(plate, idSlot);
    }
    
    /**
     * Obtiene todas las plazas reservadas del parking
     * @return Lista de slots reservados
     * @throws SQLException si hay un error en la base de datos
     */
    public ArrayList<Slot> getAllSlotsReserved() throws SQLException {
        return userManager.getAllSlotsReserved();
    }
}
