package business;

import business.model.CancelledReservation;
import business.model.Slot;
import business.model.Vehicle;
import persistence.CancelledReservationSqlDao;
import persistence.ConfigDao.ConfigJsonDao;
import persistence.SlotSqlDao;
import persistence.UserSqlDao;
import persistence.VehicleSqlDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ParkingStatusManager {

    public List<Slot> getAllSlots() {
        SlotSqlDao dao = new SlotSqlDao();
        VehicleSqlDao vehicleSqlDao = new VehicleSqlDao();
        try {
            List<Slot> slots = dao.getAllSlots();
            for (Slot slot : slots) {
                slot.setVehicleObject(vehicleSqlDao.getVehicleByPlate(slot.getVehiclePlate()));
            }
            return slots;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean cancelSlot(int slotId) {
        SlotSqlDao dao = new SlotSqlDao();
        try {
            dao.cancelSlot(slotId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public boolean createCancelledReservation(int slotId, int userId, String vehiclePlate) {
        SlotSqlDao slotDao = new SlotSqlDao();
        CancelledReservationSqlDao cancelledDao = new CancelledReservationSqlDao();
        UserSqlDao userDao = new UserSqlDao();
        VehicleSqlDao vehicleDao = new VehicleSqlDao();

        try {
            Slot slot = slotDao.getSlot(slotId);

            CancelledReservation reservation = new CancelledReservation(0, userDao.getUserById(userId), slot, vehicleDao.getVehicleByPlate(vehiclePlate));
            cancelledDao.createCancelledReservation(reservation);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int setUserNewReservationSlot(int slotId, String vehiclePlate) {
        SlotSqlDao slotDao = new SlotSqlDao();
        try {
            return slotDao.setUserNewReservationSlot(slotId, vehiclePlate);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Slot getSlot(int slotId) {
        SlotSqlDao slotDao = new SlotSqlDao();
        try {
            return slotDao.getSlot(slotId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean getFreeUnbookedSlots() {
        SlotSqlDao slotDao = new SlotSqlDao();
        List<Slot> slots = new ArrayList<>();
        try {
            slots = slotDao.getFreeUnbookedSlots();
            if (slots.isEmpty()) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
