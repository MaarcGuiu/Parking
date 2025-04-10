package business;
import business.model.Slot;
import persistence.SlotSqlDao;

import java.sql.SQLException;

public class SlotManager {
    SlotSqlDao slotSqlDao = new SlotSqlDao();
    private static int totalSlots;
    public SlotManager() {
        totalSlots = 60;                       // Creamos unas 60 plazas, y a partir de ahi las que creemos se irá sumando a ese numero
    }
    public boolean createSlot(Slot newSlot) throws SQLException {
        if (!isValidVehicleType(newSlot.getVehicle())) {
            throw new IllegalArgumentException("Invalid vehicle type.");
        }
        if (newSlot.getFloor() < 0 || newSlot.getFloor() > 3) {
            throw new IllegalArgumentException("Invalid floor number.");
        }
        totalSlots++;                           // Ester será el id de las plazas.
        slotSqlDao.createSlot(newSlot,totalSlots);

        return true;
    }
    public boolean editSlot(Slot editSlot) throws SQLException {
        Slot existingSlot = slotSqlDao.getSlot(editSlot.getIdSlot());
        if (existingSlot == null) {
            throw new IllegalArgumentException("Slot does not exist.");
        }
        if (!isValidVehicleType(editSlot.getVehicle())) {
            throw new IllegalArgumentException("Invalid vehicle type.");
        }
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
        if (slot.getAvailabilityState() == 1 || slot.getBooked() == "Booked") {
            throw new IllegalArgumentException("Slot is occupied, cannot be delete.");
        }
        slotSqlDao.deleteSlot(idSlot);

        return true;
    }
    private boolean isValidVehicleType(String vehicle) {
        switch (vehicle) {
            case "Car", "Motorbike", "Truck":return true;
            default:return false;
        }
    }

}