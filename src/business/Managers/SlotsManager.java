package business.Managers;
import business.model.Slot;
import persistence.SqlDaos.SlotSqlDao;

import java.sql.SQLException;

public class SlotsManager {
    SlotSqlDao slotSqlDao;
    private static int totalSlots;
    public SlotsManager () {
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
        if (!slot.getAvailabilityState().equals("occupied") || slot.getBooked() == "Booked") {
            throw new IllegalArgumentException("Slot is occupied, cannot be delete.");
        }
        slotSqlDao.deleteSlot(idSlot);

        return true;
    }
    private boolean isValidVehicleType(String vehicle) {
        switch (vehicle) {
            case "car", "motorbike", "ban":return true;
            default:return false;
        }
    }

}
