package persistence;

import business.model.Slot;
import business.model.Vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The type Slot sql dao.
 */
public class SlotSqlDao {
    private static Connection connection;
    private UserSqlDao userSqlDao;

    /**
     * Instantiates a new Slot sql dao.
     *
     * @throws SQLException the sql exception
     */
    public SlotSqlDao() throws SQLException {
        this.connection = ConnectionDB.getInstance();
    }

    /**
     * Gets slot.
     *
     * @param idSlot the id slot
     * @return the slot
     * @throws SQLException the sql exception
     */
//SLOT_NUMER ES LO QUE OCUPA dependiendo si es CAR, MOTORBIKE o TRUCK
    public Slot getSlot(int idSlot) throws SQLException {
        String query = "SELECT id, plant, is_occupied, slot_number FROM slots WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idSlot);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {

                    return new Slot(
                            getVehicleTypeFromSlotNumber(rs.getInt("slot_number")),
                            rs.getInt("id"),
                            rs.getInt("plant")
                    );
                }
            }
        }
        return null;
    }

    /**
     * Gets slot 2.
     *
     * @param idSlot the id slot
     * @return the slot 2
     * @throws SQLException the sql exception
     */
//String vehiclePlate, int idSlot, int isOccupeid , int floor, boolean booked, String vehicleType
    public Slot getSlot2(int idSlot) throws SQLException {
        String query = "SELECT id, plant, is_occupied, slot_number, booked, vehicle_type, vehicle_plate FROM slots WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idSlot);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {

                    return new Slot(
                            getVehicleTypeFromSlotNumber(rs.getInt("slot_number")),
                            rs.getInt("id"),
                            rs.getInt("is_occupied"),
                            rs.getInt("plant"),
                            rs.getBoolean("booked"),
                            rs.getString("vehicle_type"),
                            rs.getString("vehicle_plate")
                    );
                }
            }
        }
        return null;
    }

    /**
     * Gets total slots.
     *
     * @return the total slots
     * @throws SQLException the sql exception
     */
    public int getTotalSlots() throws SQLException {
        String query = "SELECT COUNT(slots.id) FROM slots ";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    /**
     * Gets occupied slots count.
     *
     * @return the occupied slots count
     * @throws SQLException the sql exception
     */
    public int getOccupiedSlotsCount() throws SQLException {
        String query = "SELECT COUNT(slots.id) FROM slots WHERE slots.is_occupied = 1 ";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    /**
     * Gets total slots free and not booked.
     *
     * @return the total slots free and not booked
     * @throws SQLException the sql exception
     */
    public int getTotalSlotsFreeAndNotBooked() throws SQLException {
        String query = "SELECT COUNT(slots.id) FROM slots WHERE slots.booked = 0 AND slots.is_occupied = 0 ";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    /**
     * Gets total slots not booked.
     *
     * @return the total slots not booked
     * @throws SQLException the sql exception
     */
    public int getTotalSlotsNotBooked() throws SQLException {
        String query = "SELECT COUNT(slots.id) FROM slots WHERE slots.booked = 0 ";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    /**
     * Gets num by floor.
     *
     * @param floor the floor
     * @return the num by floor
     * @throws SQLException the sql exception
     */
    public int getNumByFloor(int floor) throws SQLException {
        String query = "SELECT COUNT(slots.id) FROM slots WHERE slots.plant = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, floor);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    /**
     * Gets by floor.
     *
     * @param floor the floor
     * @return the by floor
     * @throws SQLException the sql exception
     */
    public ArrayList<Slot> getByFloor(int floor) throws SQLException {
        ArrayList<Slot> slots = new ArrayList<>();
        String query = "SELECT id, plant, is_occupied, slot_number FROM slots WHERE plant = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, floor);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Slot slot = new Slot(
                            getVehicleTypeFromSlotNumber(rs.getInt("slot_number")),
                            rs.getInt("id"),
                            rs.getInt("plant")
                    );
                    slots.add(slot);
                }
            }
        }
        return slots;
    }

    /**
     * Gets by vehicle.
     *
     * @param vehicle the vehicle
     * @return the by vehicle
     * @throws SQLException the sql exception
     */
    public ArrayList<Slot> getByVehicle(int vehicle) throws SQLException {
        ArrayList<Slot> slots = new ArrayList<>();
        String query = "SELECT id, plant, is_occupied, slot_number FROM slots WHERE slot_number = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, vehicle);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Slot slot = new Slot(
                            getVehicleTypeFromSlotNumber(rs.getInt("slot_number")),
                            rs.getInt("id"),
                            rs.getInt("plant")
                    );
                    slots.add(slot);
                }
            }
        }
        return slots;
    }

    /**
     * Edit slot.
     *
     * @param slot the slot
     * @throws SQLException the sql exception
     */
    public void editSlot(Slot slot) throws SQLException {
        if (slot.getAvailabilityState() == 1 || slot.getBooked() == true) {
            return;
        }
        String query = "UPDATE slots SET slot_number = ?, plant = ?, is_occupied = ?, vehicle_type = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, setSlotNumber(slot.getVehicle()));
            stmt.setInt(2, slot.getFloor());
            stmt.setInt(3, slot.getAvailabilityState());
            stmt.setString(4, slot.getVehicle());
            stmt.setInt(5, slot.getIdSlot());

            stmt.executeUpdate();
        }
    }

    /**
     * Create slot.
     *
     * @param slot   the slot
     * @param slotId the slot id
     * @throws SQLException the sql exception
     */
    public void createSlot(Slot slot, int slotId) throws SQLException {
        String query = "INSERT INTO slots (id, slot_number, plant, is_occupied, vehicle_type) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, slotId);
            stmt.setInt(2, setSlotNumber(slot.getVehicle()));
            stmt.setInt(3, slot.getFloor());
            stmt.setInt(4, slot.getAvailabilityState());
            stmt.setString(5, slot.getVehicle());
            stmt.executeUpdate();
        }
    }

    /**
     * Delete slot.
     *
     * @param idSlot the id slot
     * @throws SQLException the sql exception
     */
    public void deleteSlot(int idSlot) throws SQLException {
        String query = "DELETE FROM slots WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idSlot);
            stmt.executeUpdate();
        }
    }

    /**
     * Give new slot to the user boolean.
     *
     * @param slot the slot
     * @return the boolean
     * @throws SQLException the sql exception
     */
    public boolean giveNewSlotToTheUser(Slot slot) throws SQLException {
        List<Slot> slots = getAllSlots();

        for (int i = 0; i < slots.size(); i++) {
            if (slots.get(i).getAvailabilityState() == 0 && slots.get(i).getBooked() == false) {
                slots.get(i).setVehiclePlate(slot.getVehiclePlate());
                slots.get(i).setBooked(true);
                editSlot2(slots.get(i));
                return true;
            }
        }
        return false;
        //return "The slot can't be deleted becouse it isn't a free slot to change for this one to the user.";
    }

    /**
     * Edit slot 2.
     *
     * @param slot the slot
     * @throws SQLException the sql exception
     */
    public void editSlot2(Slot slot) throws SQLException {
        String query = "UPDATE slots SET booked = ?, vehicle_plate = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setBoolean(1, slot.getBooked());
            stmt.setString(2, slot.getVehiclePlate());
            stmt.setInt(3, slot.getIdSlot());

            stmt.executeUpdate();
        }
    }

    /**
     * Gets all slots.
     *
     * @return the all slots
     * @throws SQLException the sql exception
     */
    public ArrayList<Slot> getAllSlots() throws SQLException {
        ArrayList<Slot> slots = new ArrayList<>();
        String query = "SELECT vehicle_plate, plant, is_occupied, id, booked, vehicle_type FROM slots";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Slot slot = new Slot(
                        rs.getString("vehicle_plate"),
                        rs.getInt("id"),
                        rs.getInt("is_occupied"),
                        rs.getInt("plant"),
                        rs.getInt("booked") != 0,
                        rs.getString("vehicle_type")
                );
                slots.add(slot);
            }
        }
        return slots;
    }

    /**
     * Gets all slots booked.
     *
     * @return the all slots booked
     * @throws SQLException the sql exception
     */
    public ArrayList<Slot> getAllSlotsBooked() throws SQLException {
        ArrayList<Slot> slots = new ArrayList<>();
        String query = "SELECT vehicle_plate, plant, is_occupied, id, booked, vehicle_type FROM slots WHERE booked = 1";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Slot slot = new Slot(
                        rs.getString("vehicle_plate"),
                        rs.getInt("id"),
                        rs.getInt("is_occupied"),
                        rs.getInt("plant"),
                        rs.getInt("booked") != 0,
                        rs.getString("vehicle_type")
                );
                slots.add(slot);
            }
        } catch (SQLException ex) {

        }
        return slots;
    }

    /**
     * Cancel slot boolean.
     *
     * @param slotId the slot id
     * @return the boolean
     * @throws SQLException the sql exception
     */
    public boolean cancelSlot(int slotId) throws SQLException {
        String query = "UPDATE slots SET vehicle_plate = ?, booked = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, null);
            stmt.setInt(2, 0);
            stmt.setInt(3, slotId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private int setSlotNumber(String vehicle) {
        switch (vehicle) {
            case "Motorbike":
                return 2;
            case "Car":
                return 4;
            case "Truck":
                return 6;
            default:
                return 0;
        }
    }

    private String getVehicleTypeFromSlotNumber(int slotNumber) {
        switch (slotNumber) {
            case 2:
                return "Motorbike";
            case 4:
                return "Car";
            case 6:
                return "Truck";
            default:
                return "Unknown";
        }
    }

    /**
     * Gets free unbooked slots.
     *
     * @return the free unbooked slots
     * @throws SQLException the sql exception
     */
    public ArrayList<Slot> getFreeUnbookedSlots() throws SQLException { // Array de todas las plazas libres del parking
        ArrayList<Slot> slots = new ArrayList<>();
        String query = "SELECT id, plant, is_occupied, vehicle_plate, booked, vehicle_type FROM slots WHERE booked = 0 AND is_occupied = 0";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Slot slot = new Slot(
                            rs.getString("vehicle_plate"),
                            rs.getInt("id"),
                            rs.getInt("is_occupied"),
                            rs.getInt("plant"),
                            rs.getBoolean("booked"),
                            rs.getString("vehicle_type")
                    );
                    slots.add(slot);
                }
            }
        }
        return slots;
    }

    /**
     * Gets occupied slots.
     *
     * @return the occupied slots
     * @throws SQLException the sql exception
     */
    public ArrayList<Slot> getOccupiedSlots() throws SQLException { // Array de todas las plazas libres del parking
        ArrayList<Slot> slots = new ArrayList<>();
        String query = "SELECT id, plant, is_occupied, vehicle_plate, booked, vehicle_type FROM slots WHERE booked = 0 AND is_occupied = 1";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Slot slot = new Slot(
                            rs.getString("vehicle_plate"),
                            rs.getInt("id"),
                            rs.getInt("is_occupied"),
                            rs.getInt("plant"),
                            rs.getBoolean("booked"),
                            rs.getString("vehicle_type")
                    );
                    slots.add(slot);
                }
            }
        }
        return slots;
    }

    /**
     * Sets user new reservation slot.
     *
     * @param slotId       the slot id
     * @param vehiclePlate the vehicle plate
     * @return the user new reservation slot
     * @throws SQLException the sql exception
     */
// Este metodo es para cuando el admin decide cancelar una reserva y se le re asigna una al user
    // este user no se quede con la reserva de el mismo slot que el admin le ha cancelado
    public int setUserNewReservationSlot(int slotId, String vehiclePlate) throws SQLException {
        ArrayList<Slot> slots = getFreeUnbookedSlots();
        VehicleSqlDao vehicleSqlDao = new VehicleSqlDao();
        int newSlotId = -1;

        for (Slot slot : slots) {
            if (slotId != slot.getIdSlot()) {
                Vehicle vehicle = vehicleSqlDao.getVehicleByPlate(vehiclePlate);
                newSlotId = slot.getIdSlot();

                String query = "UPDATE slots SET booked = ?, vehicle_plate = ? WHERE id = ?";
                try (PreparedStatement stmt = connection.prepareStatement(query)) {
                    stmt.setBoolean(1, true);
                    stmt.setString(2, vehicle.getPlate());
                    stmt.setInt(3, slot.getIdSlot());
                    stmt.executeUpdate();
                }

                break;
            }
        }
        return newSlotId;
    }

    /**
     * Gets slot booked.
     *
     * @param vehiclePlate the vehicle plate
     * @return the slot booked
     * @throws SQLException the sql exception
     */
    public Slot getSlotBooked(String vehiclePlate) throws SQLException {
        String query = "SELECT id, plant, slot_number, is_occupied, vehicle_type FROM slots WHERE vehicle_plate = ? AND booked = 1";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, vehiclePlate);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // No cancelar la reserva aquí, solo devolver el slot
                    return new Slot(
                            rs.getString("vehicle_type") != null ? rs.getString("vehicle_type") : getVehicleTypeFromSlotNumber(rs.getInt("slot_number")),
                            rs.getInt("id"),
                            rs.getInt("is_occupied"),
                            rs.getInt("plant"),
                            true, // está reservado
                            rs.getString("vehicle_type")
                    );
                }
            }
        }
        return null;
    }

    /**
     * Update the slot booked.
     *
     * @param plate  the plate
     * @param idSlot the id slot
     * @throws SQLException the sql exception
     */
// Crear booked
    public void updateTheSlotBooked(String plate, int idSlot) throws SQLException {
        String query = "UPDATE slots SET vehicle_plate = ?,booked = 1, is_occupied = 0, reservation_date = NOW()  WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, plate);
            stmt.setInt(2, idSlot);
            stmt.executeUpdate();
        }
    }

    /**
     * Update the slot unbooked.
     *
     * @param plate  the plate
     * @param idSlot the id slot
     * @throws SQLException the sql exception
     */
// Cancelar booked
    public void updateTheSlotUnbooked(String plate,int idSlot) throws SQLException {
        userSqlDao = new UserSqlDao();
        String query = "UPDATE slots SET booked = 0, is_occupied = 0, vehicle_plate = ? WHERE vehicle_plate = ?";
        userSqlDao.registerEntryExitLogs("leave", plate, idSlot);
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, null);
            stmt.setString(2, plate);
            stmt.executeUpdate();
        }
    }

    /**
     * User entry if booked.
     *
     * @param plate the plate
     * @throws SQLException the sql exception
     */
// USER CON RESERVA ENTRA AL SLOT
    //Update del slot; de estar reservado para estar ocupado porque entra al parking
    public void userEntryIfBooked(String plate) throws SQLException {
        // Buscar el slot sin cancelar la reserva primero
        String queryFind = "SELECT id FROM slots WHERE vehicle_plate = ? AND booked = 1";
        int slotId = -1;
        
        try (PreparedStatement stmt = connection.prepareStatement(queryFind)) {
            stmt.setString(1, plate);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    slotId = rs.getInt("id");
                }
            }
        }
        
        if (slotId > 0) {
            // Ahora actualizar el slot para marcarlo como ocupado y no reservado
            String query = "UPDATE slots SET booked = 0, is_occupied = 1 WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, slotId);
                stmt.executeUpdate();
            }
        }
    }

    /**
     * User entry not booked.
     *
     * @param plate        the plate
     * @param vehicle_type the vehicle type
     * @throws SQLException the sql exception
     */
// USER SIN RESERVA ENTRA AL SLOT
    //Update del slot; de estar reservado para estar ocupado porque entra al parking
    public void userEntryNotBooked(String plate,String vehicle_type) throws SQLException {
        Slot slot = findASlotToPark(vehicle_type);
        insertVehicleIfNotExists(plate, vehicle_type);
        String query = "UPDATE slots SET vehicle_plate = ?,booked = 0, is_occupied = 1 WHERE id = ?";
        if (slot != null) {
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, plate);
                stmt.setInt(2, slot.getIdSlot());
                stmt.executeUpdate();
            }
            UserSqlDao userSqlDao = new UserSqlDao();
            userSqlDao.registerEntryExitLogs("entry", plate, slot.getIdSlot());
        }
    }

    /**
     * Insert vehicle if not exists.
     *
     * @param plate       the plate
     * @param typeVehicle the type vehicle
     * @throws SQLException the sql exception
     */
    public void insertVehicleIfNotExists(String plate, String typeVehicle) throws SQLException {
        String query = "INSERT IGNORE INTO vehicles (plate, brand, model, color, owner_id, type_vehicle) VALUES (?, 'SimBrand', 'SimModel', 'Gray', ?, ?)";
        userSqlDao = new UserSqlDao();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, plate);
            stmt.setInt(2, 1);
            stmt.setString(3, typeVehicle);
            stmt.executeUpdate();
        }

    }

    /**
     * Find a slot to park slot.
     *
     * @param vehicle_type the vehicle type
     * @return the slot
     * @throws SQLException the sql exception
     */
//Te busca una plaza libre, con el criterio de que te de la que tiene el id mas bajo
    public Slot findASlotToPark (String vehicle_type) throws SQLException {
        ArrayList<Slot> slots = getFreeUnbookedSlots();
        int lowestId = 100;
        for (Slot slotss :slots) {
            if (slotss.getIdSlot() < lowestId && Objects.equals(vehicle_type, slotss.getVehicle())) {
                lowestId = slotss.getIdSlot();
            }
        }
        return getSlot(lowestId);
    }

    /**
     * User exit.
     *
     * @param vehicle_plate the vehicle plate
     * @throws SQLException the sql exception
     */
// USER EXIT
    public void userExit (String vehicle_plate) throws SQLException {
        String query = "UPDATE slots SET vehicle_plate = ?,booked = 0, is_occupied = 0 WHERE id = ?";
        Slot slot = getSlotByPlate(vehicle_plate);

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, null);
            stmt.setInt(2, slot.getIdSlot());
            stmt.executeUpdate();
        }
    }

    /**
     * Check user vehicle is parked boolean.
     *
     * @param plate the plate
     * @return the boolean
     * @throws SQLException the sql exception
     */
    public boolean checkUserVehicleIsParked(String plate) throws SQLException { // True si el user tiene algun coche aparcado
        String query = "SELECT 1 FROM slots WHERE vehicle_plate = ? AND is_occupied = 1 LIMIT 1";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, plate);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    /**
     * Check user booking boolean.
     *
     * @param plate the plate
     * @return the boolean
     * @throws SQLException the sql exception
     */
    public boolean checkUserBooking(String plate) throws SQLException { // True si el user tiene alguna reserva
        String query = "SELECT 1 FROM slots WHERE vehicle_plate = ? AND booked = 1 LIMIT 1";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, plate);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    /**
     * Gets slot by plate.
     *
     * @param vehiclePlate the vehicle plate
     * @return the slot by plate
     * @throws SQLException the sql exception
     */
    public Slot getSlotByPlate(String vehiclePlate) throws SQLException {
        String query = "SELECT id, plant, is_occupied, slot_number FROM slots WHERE vehicle_plate = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, vehiclePlate);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {

                    return new Slot(
                            getVehicleTypeFromSlotNumber(rs.getInt("slot_number")),
                            rs.getInt("id"),
                            rs.getInt("plant")
                    );
                }
            }
        }
        return null;
    }

    /**
     * Gets panel bookings.
     *
     * @param userId the user id
     * @return the panel bookings
     * @throws SQLException the sql exception
     */
    public ArrayList<Vehicle> getPanelBookings(int userId) throws SQLException {
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        String query = "SELECT plate, brand, model, color, type_vehicle FROM vehicles WHERE owner_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Vehicle vehicle = new Vehicle(
                            rs.getString("plate"),
                            rs.getString("brand"),
                            rs.getString("model"),
                            rs.getString("color"),
                            rs.getString("type_vehicle")
                    );
                    vehicles.add(vehicle);
                }
            }
        }
        return vehicles;
    }

    /**
     * Gets all slots reserved.
     *
     * @return the all slots reserved
     * @throws SQLException the sql exception
     */
    public ArrayList<Slot> getAllSlotsReserved() throws SQLException {
        ArrayList<Slot> slots = new ArrayList<>();
        String query = "SELECT vehicle_plate, plant, is_occupied, id, booked, vehicle_type FROM slots WHERE booked = 1";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Slot slot = new Slot(
                        rs.getString("vehicle_plate"),
                        rs.getInt("id"),
                        rs.getInt("is_occupied"),
                        rs.getInt("plant"),
                        rs.getInt("booked") != 0,
                        rs.getString("vehicle_type")
                );
                slots.add(slot);
            }
        }
        return slots;
    }
}