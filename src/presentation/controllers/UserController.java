package presentation.controllers;

import business.UserManager;
import business.model.Slot;
import business.model.Vehicle;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The type User controller.
 */
public class UserController {
    private UserManager userManager;

    /**
     * Instantiates a new User controller.
     *
     * @throws SQLException the sql exception
     */
    public UserController () throws SQLException {
        this.userManager = new UserManager();
    }

    /**
     * User entry.
     */
    public void userEntry () {

    }

    /**
     * Check user booking boolean.
     *
     * @param vehiclePlate the vehicle plate
     * @return the boolean
     * @throws SQLException the sql exception
     */
    public boolean checkUserBooking (String vehiclePlate) throws SQLException {
        return userManager.checkUserBooking(vehiclePlate);
    }

    /**
     * Gets free unbooked slots.
     *
     * @return the free unbooked slots
     * @throws SQLException the sql exception
     */
//Todos los slots libres
    public ArrayList<Slot> getFreeUnbookedSlots() throws SQLException {
        return userManager.getFreeUnbookedSlots();
    }

    /**
     * Gets panel bookings.
     *
     * @param userId the user id
     * @return the panel bookings
     * @throws SQLException the sql exception
     */
// Te da todos los vehiculos que tienen reserva esto es util para el panel
    // SOLO quedaría añadir el momento que se hace la reserva con variable tipo Date
    public ArrayList<Vehicle> getPanelBookings(int userId) throws SQLException {
        return userManager.getPanelBookings(userId);
    }

    /**
     * Update the slot booked.
     *
     * @param plate  the plate
     * @param idSlot the id slot
     * @throws SQLException the sql exception
     */
// Crear booked
    public void updateTheSlotBooked(String plate, int idSlot) throws SQLException {
        userManager.updateTheSlotBooked(plate, idSlot);
    }

    /**
     * Obtiene todas las plazas reservadas del parking
     *
     * @return Lista de slots reservados
     * @throws SQLException si hay un error en la base de datos
     */
    public ArrayList<Slot> getAllSlotsReserved() throws SQLException {
        return userManager.getAllSlotsReserved();
    }
}
