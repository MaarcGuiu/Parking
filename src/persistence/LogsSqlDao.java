package persistence;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class LogsSqlDao {
    static Connection connection;

    public LogsSqlDao() {
        try {
            connection = ConnectionDB.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar con la base de datos.", e);
        }
    }

    /**
     * Obtiene todos los registros de entrada de vehículos que no tienen salida registrada
     * @return Lista de pares (matrícula, fecha entrada)
     */
    public List<VehicleEntry> getActiveVehicleEntries() throws SQLException {
        List<VehicleEntry> activeEntries = new ArrayList<>();

        String query = "SELECT e.vehicle_plate, e.timestamp " +
                "FROM entry_leave_logs e " +
                "LEFT JOIN entry_leave_logs l ON " +
                "  e.vehicle_plate = l.vehicle_plate AND " +
                "  l.action = 'leave' AND " +
                "  l.timestamp > e.timestamp " +
                "WHERE e.action = 'entry' AND l.id IS NULL";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String plate = rs.getString("vehicle_plate");
                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Madrid"));
                LocalDateTime entryTime = rs.getTimestamp("timestamp", cal).toLocalDateTime();
                activeEntries.add(new VehicleEntry(plate, entryTime));
            }
        }

        return activeEntries;
    }

    public List<VehicleEvent> getVehicleEventsLast60Minutes() throws SQLException {
        List<VehicleEvent> events = new ArrayList<>();
        LocalDateTime sixtyMinutesAgo = LocalDateTime.now().minusMinutes(60);

        String query = "SELECT vehicle_plate, action, timestamp " +
                "FROM entry_leave_logs " +
                "WHERE timestamp >= ? " +
                "ORDER BY timestamp";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setTimestamp(1, Timestamp.valueOf(sixtyMinutesAgo));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String plate = rs.getString("vehicle_plate");
                String action = rs.getString("action");
                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Madrid"));
                LocalDateTime timestamp = rs.getTimestamp("timestamp", cal).toLocalDateTime();
                events.add(new VehicleEvent(plate, action, timestamp));
            }
        }


        return events;
    }

    public static class VehicleEvent {
        private final String plate;
        private final String action; // "entry" or "leave"
        private final LocalDateTime timestamp;

        public VehicleEvent(String plate, String action, LocalDateTime timestamp) {
            this.plate = plate;
            this.action = action;
            this.timestamp = timestamp;
        }

        public String getPlate() {
            return plate;
        }
        public String getAction() {
            return action;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }
    }

    public static class VehicleEntry {
        private final String plate;
        private final LocalDateTime entryTime;

        public VehicleEntry(String plate, LocalDateTime entryTime) {
            this.plate = plate;
            this.entryTime = entryTime;
        }

        public String getPlate() {
            return plate;
        }

        public LocalDateTime getEntryTime() {
            return entryTime;
        }
    }
}