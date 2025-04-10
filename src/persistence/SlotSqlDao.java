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
        String query = "UPDATE slots SET slot_number = ?, plant = ?, is_occupied = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, setSlotNumber(slot.getVehicle()));
            stmt.setInt(2, slot.getFloor());
            stmt.setInt(3, slot.getAvailabilityState());
            stmt.setInt(4, slot.getIdSlot());

            stmt.executeUpdate();
        }
    }
    public void createSlot(Slot slot, int slotId) throws SQLException {
        String query = "INSERT INTO slots (id, slot_number, plant, is_occupied) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, slotId);
            stmt.setInt(2, setSlotNumber(slot.getVehicle()));
            stmt.setInt(3, slot.getFloor());
            stmt.setInt(4, slot.getAvailabilityState());
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
}