package business;

import business.model.Slot;
import business.model.Vehicle;
import persistence.ConfigDao.ConfigJsonDao;
import persistence.SlotSqlDao;
import persistence.UserSqlDao;
import persistence.VehicleSqlDao;

import java.sql.SQLException;
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
    public void get() {

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
}
