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
    SlotSqlDao slotSqlDao;
    UserSqlDao userSqlDao;
    VehicleSqlDao vehicleSqlDao;
    public UserManager () {
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
    public Slot getSlotBooked(String vehiclePlate) throws SQLException {
        return slotSqlDao.getSlotBooked(vehiclePlate);
    }
    public ArrayList<Slot> getFreeUnbookedSlots() throws SQLException {
        return slotSqlDao.getFreeUnbookedSlots();
    }
    public boolean isYourVehicleCorrect(Slot slot, Vehicle vehicle){
        if (Objects.equals(vehicle.getType(), slot.getVehicle())) {
            return true;
        }
        return false;
    }
}
