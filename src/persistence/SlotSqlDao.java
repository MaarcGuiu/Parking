package persistence;

import business.model.Slot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
    public ArrayList<Slot> getFreeUnbookedSlots() throws SQLException {
        ArrayList<Slot> slots = new ArrayList<>();
        String query = "SELECT id, plant, is_occupied, vehicle_plate, booked FROM slots WHERE booked = 0 AND is_occupied = 0";

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
    private void updateTheSlotUnbooked(String plate) throws SQLException {
        String query = "UPDATE slots SET booked = 0, is_occupied = 1 WHERE vehicle_plate = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, plate);
            stmt.executeUpdate();
        }
    }


    public boolean checkUserBooking(String plate) throws SQLException {
        String query = "SELECT 1 FROM slots WHERE vehicle_plate = ? AND booked = 1 LIMIT 1";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, plate);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

}