package business;

import business.model.CancelledReservation;
import business.model.Slot;
import persistence.CancelledReservationSqlDao;
import persistence.SlotSqlDao;
import persistence.UserSqlDao;
import persistence.VehicleSqlDao;
import persistence.ConfigDao.Config;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Random;


public class ParkingStatusManager {
    private Config config;
    private SlotSqlDao slotSqlDao;
    public ParkingStatusManager () {

    }
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
    // TRAFFIC SIMULATION:
    public int calculateFrequency () {
        int freq = 1;
        int vehicleTime = config.getVehicleTime();
            freq = ThreadLocalRandom.current().nextInt(1, vehicleTime + 1);
        return freq;
    }
    public void executeTimeFrequency(int freqInSeconds) {
        try {
            Thread.sleep(freqInSeconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    public boolean calculateEntryOrExit() throws SQLException { // True un vehiculo entrará, false saldrá.
        float probabilityEntry;
        probabilityEntry = slotSqlDao.getTotalSlotsFreeAndNotBooked() / slotSqlDao.getTotalSlotsNotBooked();
        if (probabilityEntry > 0.5) {
            return true;
        } else {
            return false;
        }
    }
    public void simulateEntry () throws SQLException { //Update de una plaza libre, genere aleatoriamente plate+tipo vehiculo
        String vehicle_type = "Car";
        switch (ThreadLocalRandom.current().nextInt(1, 3)) {
            case 1: vehicle_type = "Motorbike"; break;
            case 2: vehicle_type = "Car"; break;
            case 3: vehicle_type = "Truck"; break;
        }
        slotSqlDao.userEntryNotBooked(generateRandomVehiclePlate(),vehicle_type);
    }
    public Slot simulateExit () throws SQLException { // Despues de esta funcion queda hacer update de que queda libre
        ArrayList<Slot> slotsOccupied = new ArrayList<>();
        slotsOccupied = slotSqlDao.getOccupiedSlots();
        Slot slot = slotsOccupied.get(ThreadLocalRandom.current().nextInt(1, slotsOccupied.size()));
        return slot;
    }
    public String generateRandomVehiclePlate() {
        StringBuilder plate = new StringBuilder(); // Para ajuntar letras

        for (int i = 0; i < 3; i++) { // Append lo que hace es ir acomulando letras
            plate.append(generateRandomCapitalLetter());
        }
        for (int i = 0; i < 3; i++) {
            plate.append(ThreadLocalRandom.current().nextInt(0, 9));
        }
        return plate.toString(); // Lo pasamos a string fija
    }

    private char generateRandomCapitalLetter() {
        Random random = new Random();
        return (char) ('A' + random.nextInt(26));
    }



}
