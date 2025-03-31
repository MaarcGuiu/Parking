package Persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    private static Connection instance;

    private ConnectionDB() {}

    public static Connection getInstance() throws SQLException {
        if (instance == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                throw new SQLException("MySQL JDBC Driver no encontrado.");
            }
            String url = "jdbc:mysql://localhost:3306/parkingdpo?useSSL=false&serverTimezone=UTC";
            String usr = "root";
            String pwd = "";
            instance = DriverManager.getConnection(url, usr, pwd);
        }
        return instance;
    }

    public static void closeConnection() throws SQLException {
        if (instance != null) {
            try {
                instance.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
