package persistence;

import business.model.Slot;
import business.model.User;
import business.model.Vehicle;

import java.sql.*;
import java.util.ArrayList;

public class VehicleSqlDao {
    private static Connection connection;

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

    public Vehicle getVehicleByUser(User user) throws SQLException {
        String query = "SELECT plate, brand, model, color, owner_id, type_vehicle FROM vehicles WHERE owner_id = ? LIMIT 1";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, user.getId());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String plate = rs.getString("plate");
                    String brand = rs.getString("brand");
                    String model = rs.getString("model");
                    String color = rs.getString("color");
                    int ownerId = rs.getInt("owner_id");
                    String typeVehicle = rs.getString("type_vehicle");


                    return new Vehicle(plate, brand, model, color, user, typeVehicle);
                } else {
                    return null;
                }
            }
        }
    }
    public ArrayList<Vehicle> getAllVehicle() {
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        String query = "SELECT * FROM vehicles";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Vehicle vehicle = new Vehicle(
                        rs.getString("plate"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("color"),
                        rs.getString("owner_id")
                );
                vehicles.add(vehicle);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Or better error handling/logging
        }

        return vehicles;
    }
}
