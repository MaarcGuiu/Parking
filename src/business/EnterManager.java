package business;

import business.model.User;
import persistence.UserSqlDao;

import java.sql.SQLException;

public class EnterManager {

    public String registeredVehicle(String plate) {
        UserSqlDao dao = new UserSqlDao();

        try {
            return dao.registeredVehicle(plate);
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error en la base de datos.";
        }
    }

    public String isBooked(String plate) {
        UserSqlDao dao = new UserSqlDao();

        try {
            return dao.isBooked(plate);
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error en la base de datos.";
        }
    }

    public String placesAvailable(String plate, String vehicle) {
        UserSqlDao dao = new UserSqlDao();

        try {
            if (dao.vehicleExists(plate)) {
                if (!dao.sameTypeVehicle(plate, vehicle)) {
                    return "notEqual";
                }
            }
            return dao.placesAvailable(plate, vehicle);
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error en la base de datos.";
        }
    }
}
