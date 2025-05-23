package business;

import business.model.Slot;
import business.model.Vehicle;
import persistence.SlotSqlDao;
import persistence.UserSqlDao;
import persistence.VehicleSqlDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class UserManager {
    private SlotSqlDao slotSqlDao;
    private UserSqlDao userSqlDao;
    private VehicleSqlDao vehicleSqlDao;
    public UserManager () {
        this.slotSqlDao = new SlotSqlDao();
        this.userSqlDao = new UserSqlDao();
        this.vehicleSqlDao = new VehicleSqlDao();
    }

    public boolean userBooking (Slot slot) {
        if (!slot.getBooked() || slot.getAvailabilityState() == 0) {
            return false;
        }
        return true;
    }
    public boolean checkUserBooking (String vehiclePlate) throws SQLException {
        return slotSqlDao.checkUserBooking(vehiclePlate);
    }
    // Esta función es para cuando entra un vehiculo que ya tiene reserva y le devuelve el slot, además que se hace update de la plaza
    // No hay que hacer cancel
    public Slot getSlotBooked(String vehiclePlate) throws SQLException {
        return slotSqlDao.getSlotBooked(vehiclePlate);
    }
    public ArrayList<Slot> getFreeUnbookedSlots() throws SQLException {
        return slotSqlDao.getFreeUnbookedSlots();
    }
    public boolean isYourVehicleCorrect(Slot slot, Vehicle vehicle){
        if (Objects.equals(vehicle.getType(), slot.getVehicle())) {
            return true;
        } else {
            return false;
        }
    }
    // Crear booked
    public void updateTheSlotBooked(String plate, int idSlot) throws SQLException {
        slotSqlDao.updateTheSlotBooked(plate,idSlot);
    }
    // USER CON RESERVA ENTRA AL SLOT
    //Update del slot; de estar reservado para estar ocupado porque entra al parking
    public void userEntryIfbooked(String plate) throws SQLException {
        slotSqlDao.userEntryIfBooked(plate);
    }
    // USER CON RESERVA ENTRA AL SLOT // True si hay para aparcar False si esta todo lleno
    //Update del slot; de estar reservado para estar ocupado porque entra al parking y es asignado dentro de una plaza
    public boolean userEntryNotbooked(String plate, String vehicle_type) throws SQLException {
        if (!slotSqlDao.getFreeUnbookedSlots().isEmpty()) {
            slotSqlDao.userEntryNotBooked(plate,vehicle_type);
            return true;
        } else { // No hay plazas para aparcar
            return false;
        }

    }
    public boolean checkUserVehicleIsParked(String plate) throws SQLException { // True si el user tiene algun coche aparcado
        return slotSqlDao.checkUserVehicleIsParked(plate);
    }
    //User EXIT
    public void userExit (String vehicle_plate) throws SQLException {
        slotSqlDao.userExit(vehicle_plate);
    }
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
     * @return Lista de slots reservados
     * @throws SQLException si hay un error en la base de datos
     */
    public ArrayList<Slot> getAllSlotsReserved() throws SQLException {
        return slotSqlDao.getAllSlotsReserved();
    }
}
