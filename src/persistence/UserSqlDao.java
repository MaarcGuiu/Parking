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
}
