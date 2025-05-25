package business;
import business.model.Slot;
import persistence.SlotSqlDao;

import java.sql.SQLException;

public class AdminManager {
    private SlotSqlDao slotSqlDao;
    private static int totalSlots;

    public AdminManager() throws SQLException {
        this.slotSqlDao = new SlotSqlDao();
        totalSlots = 60;                       // Creamos unas 60 plazas, y a partir de ahi las que creemos se irá sumando a ese numero
    }

    public String createSlot(Slot newSlot) throws SQLException {
        Slot slot = slotSqlDao.getSlot(newSlot.getIdSlot());
        if (slot != null) {
            return "Slot with id " + slot.getIdSlot() + " already exists";
        }
        if (!isValidVehicleType(newSlot.getVehicle())) {
            throw new IllegalArgumentException("Invalid vehicle type.");
        }
        if (newSlot.getFloor() < 0 || newSlot.getFloor() > 3) {
            return "Invalid floor.";
        }
        totalSlots++;                           // Ester será el id de las plazas.
        slotSqlDao.createSlot(newSlot,newSlot.getIdSlot());

        return "Slot with id " + newSlot.getIdSlot() + " created";
    }

    public boolean editSlot(Slot editSlot) throws SQLException {
        Slot existingSlot = slotSqlDao.getSlot2(editSlot.getIdSlot());
        if (existingSlot == null) {
            return false;
        }
        if (existingSlot.getAvailabilityState() == 1 || existingSlot.getBooked() == true ) {
            return false;
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

    public String deleteSlot(int idSlot) throws SQLException {
        Slot slot = slotSqlDao.getSlot2(idSlot);

        if (slot == null) {
            return "Slot doesn't exist.";
        }

        if (slot.getAvailabilityState() == 1 && slot.getBooked() == true) {
            return "Slot is occupied, cannot be delete.";
        }

        if (slot.getAvailabilityState() == 0 && slot.getBooked() == true) {
            if (slotSqlDao.giveNewSlotToTheUser(slot)) {
                slotSqlDao.deleteSlot(idSlot);
                return "The Slot has been deleted, the user book was changed";
            }
            return "The slot can't be deleted becouse it isn't a free slot to change for this one to the user.";
        }

        if (slot.getAvailabilityState() == 0 || slot.getBooked() == false) {
            slotSqlDao.deleteSlot(idSlot);
        }

        return "Slot deleted.";
    }

    private boolean isValidVehicleType(String vehicle) {
        switch (vehicle) {
            case "Car", "Motorbike", "Truck":return true;
            default:return false;
        }
    }

    public int getNumByFloor (int floor) throws SQLException {
        return slotSqlDao.getNumByFloor(floor);
    }

    public int getTotalSlots () throws SQLException {
        return slotSqlDao.getTotalSlots();
    }

}