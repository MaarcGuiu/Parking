package business;

import business.model.CancelledReservation;
import business.model.Slot;
import business.model.Vehicle;
import persistence.CancelledReservationSqlDao;
import persistence.ConfigDao.ConfigJsonDao;
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
    private ConfigJsonDao configJsonDao;
    private SlotSqlDao slotSqlDao;
    private VehicleSqlDao vehicleSqlDao;

    public ParkingStatusManager () throws SQLException {
        slotSqlDao = new SlotSqlDao();
    }

    public List<Slot> getAllSlots() throws SQLException {
        SlotSqlDao dao = new SlotSqlDao();
        VehicleSqlDao vehicleSqlDao = new VehicleSqlDao();

        List<Slot> slots = dao.getAllSlots();
        for (Slot slot : slots) {
            slot.setVehicleObject(vehicleSqlDao.getVehicleByPlate(slot.getVehiclePlate()));
        }
        return slots;

    }

        public boolean cancelSlot(int slotId) throws SQLException {
            SlotSqlDao dao = new SlotSqlDao();
            dao.cancelSlot(slotId);
            return true;
        }

    public boolean createCancelledReservation(int slotId, int userId, String vehiclePlate) throws SQLException {
        CancelledReservationSqlDao cancelledDao = new CancelledReservationSqlDao();
        UserSqlDao userDao = new UserSqlDao();
        VehicleSqlDao vehicleDao = new VehicleSqlDao();

        try {
            Slot slot = slotSqlDao.getSlot(slotId);

            CancelledReservation reservation = new CancelledReservation(0, userDao.getUserById(userId), slot, vehicleDao.getVehicleByPlate(vehiclePlate));
            cancelledDao.createCancelledReservation(reservation);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int setUserNewReservationSlot(int slotId, String vehiclePlate) throws SQLException {
        SlotSqlDao slotDao = new SlotSqlDao();
        return slotDao.setUserNewReservationSlot(slotId, vehiclePlate);
    }

    public Slot getSlot(int slotId) throws SQLException {
        SlotSqlDao slotDao = new SlotSqlDao();
        return slotDao.getSlot(slotId);
    }

    public boolean getFreeUnbookedSlots() throws SQLException {
        SlotSqlDao slotDao = new SlotSqlDao();
        List<Slot> slots = new ArrayList<>();
        slots = slotDao.getFreeUnbookedSlots();
        if (slots.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
    // TRAFFIC SIMULATION:
    public int calculateFrequency () {
        int freq = 1;
        configJsonDao = new ConfigJsonDao();
        Config config = configJsonDao.loadAllConfig();
        int vehicleTime = config.getVehicleTime();
            System.out.println("vehTime: "+vehicleTime);
            freq = ThreadLocalRandom.current().nextInt(1, vehicleTime);
        return freq;
    }
    public boolean calculateEntryOrExit() throws SQLException { // True un vehiculo entrará, false saldrá.
        float probabilityEntry;
        System.out.println("\nNumerador: "+slotSqlDao.getTotalSlotsFreeAndNotBooked()+"/");System.out.println("Denominador: "+slotSqlDao.getTotalSlotsNotBooked());
        probabilityEntry = (float) slotSqlDao.getTotalSlotsFreeAndNotBooked() / slotSqlDao.getTotalSlotsNotBooked();
        System.out.println("\nCalcul: "+probabilityEntry);
        if (probabilityEntry > 0.49) { //Entry
            return true;
        } else {                      //Exit
            return false;
        }
    }
    public void simulateEntry () throws SQLException { //Update de una plaza libre, genere aleatoriamente plate+tipo vehiculo
        String vehicle_type = "Car";
        //String randomPlate = randomVehicle().getPlate();
        switch (ThreadLocalRandom.current().nextInt(1, 3)) {
            case 1: vehicle_type = "Motorbike"; break;
            case 2: vehicle_type = "Car"; break;
            case 3: vehicle_type = "Truck"; break;
        }
        String randomPlate = generateRandomVehiclePlate();
        System.out.println("plateEntry: \n"+ randomPlate);
        slotSqlDao.userEntryNotBooked(randomPlate,vehicle_type);
    }
    public Vehicle randomVehicle () throws SQLException {
        vehicleSqlDao = new VehicleSqlDao();
        ArrayList<Vehicle> allVehicles = vehicleSqlDao.getAllVehicle();
        ArrayList<Slot> allSlots = slotSqlDao.getAllSlots();
        boolean flag;
        Vehicle vv;
        do {
            flag = true;
            vv = allVehicles.get(ThreadLocalRandom.current().nextInt(allVehicles.size()));
            for (Slot s : allSlots) {
                if (s.getAvailabilityState() == 1 && s.getVehicle().equals(vv)) {  // is_occupied = 1 falta && not booked
                    flag = false;
                }
            }
        } while (!flag);
        return vv;
    }
    public Slot simulateExit () throws SQLException { // Despues de esta funcion queda hacer update de que queda libre
        ArrayList<Slot> slotsOccupied = new ArrayList<>();
        slotsOccupied = slotSqlDao.getOccupiedSlots();
        if (slotsOccupied.isEmpty()) {
            throw new IllegalStateException("No occupied slots.");
        }
        Slot slot = slotsOccupied.get(ThreadLocalRandom.current().nextInt(slotsOccupied.size()));
        String randomPlate = slot.getVehiclePlate();
        slotSqlDao.updateTheSlotUnbooked(randomPlate,slot.getIdSlot()); // Aqui hago el update
        System.out.println("plateExit: \n"+ randomPlate);
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
