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
        String query = "SELECT id, plant,slot_number ,is_occupied,vehicle_plate FROM slots WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idSlot);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {

                    return new Slot(
                            rs.getInt("id"),
                            rs.getInt("plant"),
                            getVehicleTypeFromSlotNumber(rs.getInt("slot_number")),
                            rs.getInt("is_occupied"),
                            rs.getString("vehicle_plate")
                    );
                }
            }
        }
        return null;
    }

    public ArrayList<Slot> getByFloor(int floor) throws SQLException {
        ArrayList<Slot> slots = new ArrayList<>();
        String query = "SELECT id, plant,slot_number, is_occupied,vehicle_plate  FROM slots WHERE plant = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, floor);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Slot slot = new Slot(
                            rs.getInt("id"),
                            rs.getInt("plant"),
                            getVehicleTypeFromSlotNumber(rs.getInt("slot_number")),
                            rs.getInt("is_occupied"),
                            rs.getString("vehicle_plate")
                    );
                    slots.add(slot);
                }
            }
        }
        return slots;
    }

    public ArrayList<Slot> getByVehicle(String vehicle) throws SQLException {
        ArrayList<Slot> slots = new ArrayList<>();
        String query = "SELECT id, plant,slot_number ,is_occupied,vehicle_plate FROM slots WHERE slot_number = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, setSlotNumber(vehicle));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Slot slot = new Slot(
                            rs.getInt("id"),
                            rs.getInt("plant"),
                            getVehicleTypeFromSlotNumber(rs.getInt("slot_number")),
                            rs.getInt("is_occupied"),
                            rs.getString("vehicle_plate")
                    );
                    slots.add(slot);
                }
            }
        }
        return slots;
    }
    public void editSlot(Slot slot) throws SQLException {
        String query = "UPDATE slots SET id = ?, plant = ?, slot_number = ?, is_occupied = ?,vehicle_plate = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, slot.getIdSlot());
            stmt.setInt(2, slot.getFloor());
            stmt.setInt(3, setSlotNumber(slot.getType()));
            stmt.setInt(4, slot.getAvailabilityState());
            stmt.setString(5, slot.getVehiclePlate());
            stmt.setInt(6, slot.getIdSlot());
            stmt.executeUpdate();
        }
    }
    public void createSlot(Slot slot) throws SQLException {
        String query = "INSERT INTO slots (id,plant, slot_number, is_occupied,vehicle_plate) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, slot.getIdSlot());
            stmt.setInt(2, slot.getFloor());
            stmt.setInt(3, setSlotNumber(slot.getType()));        // Lo pasamos de String a int para guardarlo en la bbdd
            stmt.setInt(4, slot.getAvailabilityState());
            stmt.setString(5, slot.getVehiclePlate());
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