package persistence.SqlDaos;

import persistence.ConfigDao.Config;
import persistence.ConfigDao.ConfigDao;
import persistence.ConfigDao.ConfigJsonDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    private static Connection instance;

    private ConnectionDB() {}

    public static Connection getInstance() throws SQLException {
        if (instance == null) {
            ConfigDao configDao = new ConfigJsonDao();
            Config config = configDao.loadAllConfig();
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                throw new SQLException("MySQL JDBC Driver no encontrado.");
            }
            String url = config.getServerIp();
            String usr = config.getUser();
            String pwd = config.getPwd();
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
