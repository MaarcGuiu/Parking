package business;
import business.model.Slot;
import persistence.SlotSqlDao;

import java.security.PublicKey;
import java.sql.SQLException;
import java.util.ArrayList;

public class SlotManager {
    SlotSqlDao slotSqlDao = new SlotSqlDao();
    private static int totalSlots;
    public SlotManager() {
    }
    public boolean createSlot(Slot newSlot) throws SQLException {
        if (slotSqlDao.getSlot(newSlot.getIdSlot()) != null) {
            throw new IllegalArgumentException("Slot ID already exists.");
        }
        /*if (!isValidVehicleType(newSlot.getVehiclePlate())) {
            throw new IllegalArgumentException("Invalid vehicle type.");
        }*/
        if (newSlot.getFloor() < 0 || newSlot.getFloor() > 3) {
            throw new IllegalArgumentException("Invalid floor number.");
        }
        totalSlots++;                           // Ester será el id de las plazas.
        slotSqlDao.createSlot(newSlot);

        return true;
    }
    public boolean editSlot(Slot editSlot) throws SQLException {
        Slot existingSlot = slotSqlDao.getSlot(editSlot.getIdSlot());
        if (existingSlot == null) {
            throw new IllegalArgumentException("Slot does not exist.");
        }
        /*if (!isValidVehicleType(editSlot.getVehiclePlate())) {
            throw new IllegalArgumentException("Invalid vehicle type.");
        }*/
        if (editSlot.getFloor() < 0 || editSlot.getFloor() > 3) { // Suponemos que hay maximo 3 plantas
            throw new IllegalArgumentException("Invalid floor number.");
        }
        slotSqlDao.editSlot(editSlot);

        return true;
    }
    public boolean deleteSlot(int idSlot) throws SQLException {
        Slot slot = slotSqlDao.getSlot(idSlot);

        if (slot == null) {
            throw new IllegalArgumentException("Slot doesn't exist.");
        }
        if (slot.getAvailabilityState() == 1 || slot.getBooked().equalsIgnoreCase("Booked")) {
            throw new IllegalArgumentException("Slot is occupied, cannot be delete.");
        }
        slotSqlDao.deleteSlot(idSlot);

        return true;
    }
    /*private boolean isValidVehicleType(String vehicle) {
        switch (vehicle) {
            case "Car", "Motorbike", "Truck":return true;
            default:return false;
        }
    }*/
    public Slot getSlot(int idSlot) throws SQLException {
        return slotSqlDao.getSlot(idSlot);
    }
    public ArrayList<Slot> getByVehicle(String vehicle) throws SQLException {
        return slotSqlDao.getByVehicle(vehicle);
    }
    public ArrayList<Slot> getByFloor(int floor) throws SQLException {
        return slotSqlDao.getByFloor(floor);
    }

}