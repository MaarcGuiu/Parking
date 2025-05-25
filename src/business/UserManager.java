package business;

import business.model.Slot;
import business.model.Vehicle;
import persistence.SlotSqlDao;
import persistence.UserSqlDao;
import persistence.VehicleSqlDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * The type User manager.
 */
public class UserManager {
    private SlotSqlDao slotSqlDao;
    private UserSqlDao userSqlDao;
    private VehicleSqlDao vehicleSqlDao;

    /**
     * Instantiates a new User manager.
     *
     * @throws SQLException the sql exception
     */
    public UserManager () throws SQLException{
        this.slotSqlDao = new SlotSqlDao();
        this.userSqlDao = new UserSqlDao();
        this.vehicleSqlDao = new VehicleSqlDao();
    }

    /**
     * Check user booking boolean.
     *
     * @param vehiclePlate the vehicle plate
     * @return the boolean
     * @throws SQLException the sql exception
     */
    public boolean checkUserBooking (String vehiclePlate) throws SQLException {
        return slotSqlDao.checkUserBooking(vehiclePlate);
    }

    /**
     * Gets free unbooked slots.
     *
     * @return the free unbooked slots
     * @throws SQLException the sql exception
     */
    public ArrayList<Slot> getFreeUnbookedSlots() throws SQLException {
        return slotSqlDao.getFreeUnbookedSlots();
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
        slotSqlDao.updateTheSlotBooked(plate,idSlot);
    }

    /**
     * Gets panel bookings.
     *
     * @param userId the user id
     * @return the panel bookings
     * @throws SQLException the sql exception
     */
    public ArrayList<Vehicle> getPanelBookings(int userId) throws SQLException {
        ArrayList<Vehicle> vehicles = slotSqlDao.getPanelBookings(userId);
        ArrayList<Vehicle> bookedVehicles = new ArrayList<>();

        if (vehicles.isEmpty()) {
            return new ArrayList<>();
        } else {
            for (Vehicle v : vehicles) {
                if (slotSqlDao.checkUserBooking(v.getPlate())) {
                    bookedVehicles.add(v);
                }
            }
            return bookedVehicles;
        }
    }

    /**
     * Obtiene todas las plazas reservadas del parking
     *
     * @return Lista de slots reservados
     * @throws SQLException si hay un error en la base de datos
     */
    public ArrayList<Slot> getAllSlotsReserved() throws SQLException {
        return slotSqlDao.getAllSlotsReserved();
    }
}
