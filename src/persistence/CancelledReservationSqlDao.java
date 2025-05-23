package persistence;

import business.model.CancelledReservation;
import business.model.Slot;
import business.model.User;
import business.model.Vehicle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CancelledReservationSqlDao {
    private static Connection connection;

    public CancelledReservationSqlDao() {
        try {
            this.connection = ConnectionDB.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar con la base de datos.", e);
        }
    }

    public void createCancelledReservation(CancelledReservation reservation) throws SQLException {
        String query = "INSERT INTO cancelled_reservations (user_id, slot_id, vehicle_plate) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, reservation.getUser().getId());
            stmt.setInt(2, reservation.getSlot().getIdSlot());
            stmt.setString(3, reservation.getVehicle().getPlate());
            stmt.executeUpdate();
        }
    }

    public List<CancelledReservation> getCancelledReservationsByUserId(int userId) throws SQLException {
        List<CancelledReservation> cancelledReservations = new ArrayList<>();
        UserSqlDao userSqlDao = new UserSqlDao();
        SlotSqlDao slotSqlDao = new SlotSqlDao();
        VehicleSqlDao vehicleSqlDao = new VehicleSqlDao();

        String query = "SELECT id, slot_id, vehicle_plate FROM cancelled_reservations WHERE user_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int slotId = rs.getInt("slot_id");

                    User user = userSqlDao.getUserById(userId);       // Constructor básico
                    Slot slot = slotSqlDao.getSlot(slotId);       // Constructor con solo ID
                    Vehicle vehicle = vehicleSqlDao.getVehicleByPlate(rs.getString("vehicle_plate"));

                    CancelledReservation reservation = new CancelledReservation(id, user, slot, vehicle);
                    cancelledReservations.add(reservation);
                }
            }
        }

        return cancelledReservations;
    }

    public void deleteCancelledReservationsById(int id) throws SQLException {
        String query = "DELETE FROM cancelled_reservations WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
