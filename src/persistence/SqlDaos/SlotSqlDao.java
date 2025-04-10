package persistence.SqlDaos;

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
    public Slot getSlot(int idSlot) throws SQLException {
        String query = "SELECT idSlot, vehicle, floor, availabilityState FROM slot WHERE idSlot = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idSlot);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Slot(
                            rs.getString("vehicle"),
                            rs.getInt("idSlot"),
                            rs.getInt("floor")
                    );
                }
            }
        }
        return null;
    }

    public ArrayList<Slot> getByFloor(int floor) throws SQLException {
        ArrayList<Slot> slots = new ArrayList<>();
        String query = "SELECT idSlot, vehicle, floor, availabilityState FROM slot WHERE floor = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, floor);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Slot slot = new Slot(
                            rs.getString("vehicle"),
                            rs.getInt("idSlot"),
                            rs.getInt("floor")
                    );
                    slots.add(slot);
                }
            }
        }
        return slots;
    }

    public ArrayList<Slot> getByVehicle(int vehicle) throws SQLException {
        ArrayList<Slot> slots = new ArrayList<>();
        String query = "SELECT idSlot, vehicle, floor, availabilityState FROM slot WHERE vehicle = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, vehicle);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Slot slot = new Slot(
                            rs.getString("vehicle"),
                            rs.getInt("idSlot"),
                            rs.getInt("floor")
                    );
                    slots.add(slot);
                }
            }
        }
        return slots;
    }
    public void editSlot(Slot slot) throws SQLException {
        String query = "UPDATE slot SET vehicle = ?, floor = ?, availabilityState = ?, booked = ? WHERE idSlot = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, slot.getVehicle());
            stmt.setInt(2, slot.getFloor());
            stmt.setString(3, slot.getAvailabilityState());
            stmt.setString(4, slot.getBooked());
            stmt.setInt(5, slot.getIdSlot());

            stmt.executeUpdate();
        }
    }
    public void createSlot(Slot slot, int slotId) throws SQLException {
        String query = "INSERT INTO slot (idSlot, vehicle, floor, availabilityState, booked) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, slotId);
            stmt.setString(2, slot.getVehicle());
            stmt.setInt(3, slot.getFloor());
            stmt.setString(4, slot.getAvailabilityState());
            stmt.setBoolean(5, false);
            stmt.executeUpdate();
        }
    }
    public void deleteSlot(int idSlot) throws SQLException {
        String query = "DELETE FROM slot WHERE idSlot = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idSlot);
            stmt.executeUpdate();
        }
    }
}
