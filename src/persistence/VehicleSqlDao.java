package persistence;

import business.model.User;
import business.model.Vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VehicleSqlDao {
    static Connection connection;

    public VehicleSqlDao() {
        try {
            this.connection = ConnectionDB.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar con la base de datos.", e);
        }
    }

    public Vehicle getVehicleByPlate(String vehiclePlate) throws SQLException {
        String vehicleQuery = "SELECT plate, brand, model, color, owner_id, type_vehicle FROM vehicles WHERE plate = ?";
        try (PreparedStatement stmt = connection.prepareStatement(vehicleQuery)) {
            stmt.setString(1, vehiclePlate);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String plate = rs.getString("plate");
                    String brand = rs.getString("brand");
                    String model = rs.getString("model");
                    String color = rs.getString("color");
                    int ownerId = rs.getInt("owner_id");
                    String typeVehicle = rs.getString("type_vehicle");

                    UserSqlDao userSqlDao = new UserSqlDao();
                    User owner = userSqlDao.getUserById(ownerId);

                    return new Vehicle(plate, brand, model, color, owner, typeVehicle);
                } else {
                    return null;
                }
            }
        }
    }
}
