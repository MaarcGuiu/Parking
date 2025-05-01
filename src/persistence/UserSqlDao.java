package persistence;

import business.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserSqlDao {
    static Connection connection;

    public UserSqlDao() {
        try {
            this.connection = ConnectionDB.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar con la base de datos.", e);
        }
    }

    public String login(String emailOrName, String password, String adminPwd) throws SQLException {
        //Verificar si es admin
        if ("admin".equals(emailOrName)) {
            if (password.equals(adminPwd)) {
                return "admin_success";
            } else {
                return "Contraseña de administrador incorrecta.";
            }
        }

        // Verificar si el usuario/email existe en la base de datos
        String checkUserQuery = "SELECT password FROM users WHERE username = ? OR email = ?";
        try (PreparedStatement checkUserStmt = connection.prepareStatement(checkUserQuery)) {
            checkUserStmt.setString(1, emailOrName);
            checkUserStmt.setString(2, emailOrName);

            try (ResultSet rs = checkUserStmt.executeQuery()) {
                if (!rs.next()) {
                    return "Usuario no encontrado.";
                }

                // Comparar la contraseña
                String storedPassword = rs.getString("password");
                if (!storedPassword.equals(password)) {
                    return "Contraseña incorrecta.";
                }

                // Si todo está correcto, iniciar sesión
                return "success";
            }
        }
    }

    public User getUser(String emailOrName) throws SQLException {
        String query = "SELECT id, username, password, email FROM users WHERE username = ? OR email = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, emailOrName);
            stmt.setString(2, emailOrName);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {  // Si encuentra un usuario
                    return new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("email")
                    );
                }
            }
        }

        return null;
    }

    public User getUserById(int id) throws SQLException {
        String query = "SELECT id, username, password, email FROM users WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("email")
                    );
                }
            }
        }

        return null;
    }

    public String register(String username, String password, String email) throws SQLException {
        // 1. Verificar si el nombre de usuario ya existe
        if (getUser(username) != null) {
            return "El nombre de usuario ya está en uso.";
        }

        // 2. Verificar si el correo electrónico ya existe
        if (getUser(email) != null) {
            return "El correo electrónico ya está registrado.";
        }

        // 3. Insertar el nuevo usuario
        String insertQuery = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
        try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
            insertStmt.setString(1, username);
            insertStmt.setString(2, password); // ⚠ Aquí puedes aplicar hashing
            insertStmt.setString(3, email);

            int affectedRows = insertStmt.executeUpdate();
            if (affectedRows > 0) {
                return "success";
            } else {
                return "Error al registrar el usuario.";
            }
        }
    }

    public String deleteAccount(String emailOrName, String password) throws SQLException {
        // Primero, verificar si el usuario/email existe y la contraseña es correcta
        String checkUserQuery = "SELECT id, password FROM users WHERE username = ? OR email = ?";
        try (PreparedStatement checkUserStmt = connection.prepareStatement(checkUserQuery)) {
            checkUserStmt.setString(1, emailOrName);
            checkUserStmt.setString(2, emailOrName);

            try (ResultSet rs = checkUserStmt.executeQuery()) {
                if (!rs.next()) {
                    return "Usuario no encontrado.";
                }

                // Comparar la contraseña
                String storedPassword = rs.getString("password");
                if (!storedPassword.equals(password)) {
                    return "Contraseña incorrecta.";
                }

                // Si la verificación es exitosa, proceder a eliminar la cuenta
                int userId = rs.getInt("id");
                String deleteQuery = "DELETE FROM users WHERE id = ?";
                try (PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery)) {
                    deleteStmt.setInt(1, userId);
                    int rowsAffected = deleteStmt.executeUpdate();

                    if (rowsAffected > 0) {
                        return "success"; // Cuenta eliminada exitosamente
                    } else {
                        return "Error al eliminar la cuenta.";
                    }
                }
            }
        }
    }

    public String userPlate(User loggedUser, String plate) throws SQLException {
        //Verificar si l'usuari té aquesta matrícula assignada.
        String query = "SELECT * FROM vehicles WHERE plate = ? AND owner_id = ?;";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, plate);
            stmt.setInt(2, loggedUser.getId());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return "success";
                }
            }
        }

        //Comprovem addicionalment si aquesta matrícula està dins del parking o no hi és.
        String query2 = "SELECT * FROM vehicles WHERE plate = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query2)) {
            stmt.setString(1, plate);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return "This vehicle belongs to another user.";
                } else {
                    return "No user has this license plate registered.";
                }
            }
        }
    }

    public String plateInside(User loggedUser, String plate) throws SQLException {
        //Verificar si el vehicle està opcupant una plaça
        String checkSlotQuery = "SELECT * FROM slots WHERE vehicle_plate = ? AND is_occupied = 1";

        try (PreparedStatement slotStmt = connection.prepareStatement(checkSlotQuery)) {
            slotStmt.setString(1, plate);

            try (ResultSet rs = slotStmt.executeQuery()) {
                if (!rs.next()) {
                    return "The vehicle is not in the parking lot.";
                }
            }
        }

        return "success";
    }

    public String updatePlate(User loggedUser, String plate) throws SQLException {
        //Alliberem l'slot que l'usuari ha deixat lliure.
        String updateSlotQuery = "UPDATE slots SET is_occupied = 0, booked = 0, vehicle_plate = NULL WHERE vehicle_plate = ?";

        try (PreparedStatement updateStmt = connection.prepareStatement(updateSlotQuery)) {
            updateStmt.setString(1, plate);
            updateStmt.executeUpdate();
        }

        return "success";
    }

    public String registeredVehicle(User loggedUser, String plate) throws SQLException {
        //Mirem si el vehicle està registrat
        String vehicleRegisteredQuery = "SELECT * FROM vehicles WHERE plate = ?";
        try (PreparedStatement checkVehicleStmt = connection.prepareStatement(vehicleRegisteredQuery)) {
            checkVehicleStmt.setString(1, plate);
            try (ResultSet vehicleRs = checkVehicleStmt.executeQuery()) {
                if (!vehicleRs.next()) {
                    // No existeix el vehicle
                    return "The vehicle entered is not registered.";
                }
            }
        }

        //Comprovem si està dins del pàrking
        String checkSlotQuery = "SELECT * FROM slots WHERE vehicle_plate = ? AND is_occupied = 1";
        try (PreparedStatement checkSlotStmt = connection.prepareStatement(checkSlotQuery)) {
            checkSlotStmt.setString(1, plate);
            try (ResultSet slotRs = checkSlotStmt.executeQuery()) {
                if (slotRs.next()) {
                    // El vehicle està dins del pàrquing
                    return "is_inside";
                } else {
                    // El vehicle està registrat però fora del pàrquing
                    return "success";
                }
            }
        }
    }

    public String isBooked(User loggedUser, String plate) throws SQLException {
        //Mirem si el vehicle té reservada una plaça o no
        String isBookedQuery = "SELECT * FROM slots WHERE vehicle_plate = ? AND booked = 1";
        String updateSlotQuery = "UPDATE slots SET is_occupied = 1, vehicle_plate = ? WHERE id = ?";

        try (PreparedStatement checkSlotStmt = connection.prepareStatement(isBookedQuery)) {
            checkSlotStmt.setString(1, plate);
            try (ResultSet slotRs = checkSlotStmt.executeQuery()) {
                if (slotRs.next()) {
                    int slotId = slotRs.getInt("id");
                    try (PreparedStatement updateSlotStmt = connection.prepareStatement(updateSlotQuery)) {
                        updateSlotStmt.setString(1, plate);
                        updateSlotStmt.setInt(2, slotId);
                        updateSlotStmt.executeUpdate();
                    }
                    return "success";
                } else {
                    return null;
                }
            }
        }
    }

    public String placesAvailable(User loggedUser, String plate, String vehicleType) throws SQLException {
        //Mirem si hi ha places disponibles, és a dir, que no estiguin ocupades ni reservades i que coincideixin amb el vehicle introduït.
        String placesAvailableQuery = "SELECT id, plant, slot_number, is_occupied, vehicle_plate, booked, vehicle_type " +
                "FROM slots WHERE is_occupied = 0 AND booked = 0 AND vehicle_type = ? LIMIT 1";
        String updateSlotQuery = "UPDATE slots SET is_occupied = 1, vehicle_plate = ? WHERE id = ?";

        try (PreparedStatement findSlotStmt = connection.prepareStatement(placesAvailableQuery)) {
            findSlotStmt.setString(1, vehicleType);
            try (ResultSet slotRs = findSlotStmt.executeQuery()) {
                if (slotRs.next()) {
                    int plant = slotRs.getInt("plant");
                    int slotNumber = slotRs.getInt("slot_number");
                    int slotId = slotRs.getInt("id");

                    try (PreparedStatement updateSlotStmt = connection.prepareStatement(updateSlotQuery)) {
                        updateSlotStmt.setString(1, plate);
                        updateSlotStmt.setInt(2, slotId);
                        updateSlotStmt.executeUpdate();
                    }

                    return "The vehicle has been assigned to plant " + plant + ", slot number " + slotNumber;
                } else {
                    return null;
                }
            }
        }
    }

    public boolean sameTypeVehicle(User loggedUser, String plate, String vehicle) throws SQLException {
        //Comprovem que el tipus de vehicle que ens han introduït sigui el mateix tipus que el que tenim registrat, utilitzant la matrícula per comprovar-ho
        String sameVehicleQuery = "SELECT * FROM vehicles WHERE plate = ? AND type_vehicle = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sameVehicleQuery)) {
            stmt.setString(1, plate);
            stmt.setString(2, vehicle);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }
}