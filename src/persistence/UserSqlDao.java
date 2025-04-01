package persistence;

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

    public String login(String emailOrName, String password) throws SQLException {
        // Primero, verificar si el usuario/email existe en la base de datos
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
}
