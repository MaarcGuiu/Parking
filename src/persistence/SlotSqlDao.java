package persistence;

import business.model.Slot;
import business.model.Vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class SlotSqlDao {
    static Connection connection;

    public SlotSqlDao() {
        try {
            this.connection = ConnectionDB.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar con la base de datos.", e);
        }
    }

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
    public void editSlot(Slot slot) throws SQLException {
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
    public void deleteSlot(int idSlot) throws SQLException {
        String query = "DELETE FROM slots WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idSlot);
            stmt.executeUpdate();
        }
    }

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
        }
        return slots;
    }

    public void cancelSlot(int slotId) throws SQLException {
        String query = "UPDATE slots SET vehicle_plate = ?, booked = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, null);
            stmt.setInt(2, 0);
            stmt.setInt(3, slotId);

            stmt.executeUpdate();
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

    public Slot getSlotBooked(String vehiclePlate) throws SQLException {
        String query = "SELECT id, plant, slot_number,is_occupied FROM slots WHERE vehicle_plate = ? AND booked = 1";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, vehiclePlate);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    updateTheSlotUnbooked(vehiclePlate);
                    return new Slot(
                            getVehicleTypeFromSlotNumber(rs.getInt("slot_number")),
                            rs.getInt("id"),
                            rs.getInt("plant"),
                            1
                    );
                }
            }
        }
        return null;
    }
    // Crear booked
    public void updateTheSlotBooked(String plate, int idSlot) throws SQLException {
        String query = "UPDATE slots SET vehicle_plate = ?,booked = 1, is_occupied = 0 WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, plate);
            stmt.setInt(2, idSlot);
            stmt.executeUpdate();
        }
    }
    // Cancelar booked
    public void updateTheSlotUnbooked(String plate) throws SQLException {
        String query = "UPDATE slots SET booked = 0, is_occupied = 0, vehicle_plate = ? WHERE vehicle_plate = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, null);
            stmt.setString(2, plate);
            stmt.executeUpdate();
        }
    }
    // USER CON RESERVA ENTRA AL SLOT
    //Update del slot; de estar reservado para estar ocupado porque entra al parking
    public void userEntryIfBooked(String plate) throws SQLException {
        Slot slot = getSlotBooked(plate);

        String query = "UPDATE slots SET vehicle_plate = ?,booked = 0, is_occupied = 1 WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, plate);
            stmt.setInt(2, slot.getIdSlot());
            stmt.executeUpdate();
        }
    }
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
        }
    }
    public void insertVehicleIfNotExists(String plate, String typeVehicle) throws SQLException {
        String query = "INSERT IGNORE INTO vehicles (plate, brand, model, color, owner_id, type_vehicle) VALUES (?, 'SimBrand', 'SimModel', 'Gray', 1, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, plate);
            stmt.setString(2, typeVehicle);
            stmt.executeUpdate();
        }
    }

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
    public boolean checkUserVehicleIsParked(String plate) throws SQLException { // True si el user tiene algun coche aparcado
        String query = "SELECT 1 FROM slots WHERE vehicle_plate = ? AND is_occupied = 1 LIMIT 1";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, plate);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }
    public boolean checkUserBooking(String plate) throws SQLException { // True si el user tiene alguna reserva
        String query = "SELECT 1 FROM slots WHERE vehicle_plate = ? AND booked = 1 LIMIT 1";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, plate);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

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
}