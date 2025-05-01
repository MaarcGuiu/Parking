package business;

import business.model.User;
import persistence.UserSqlDao;

import java.sql.SQLException;

public class EnterManager {

    public String registeredVehicle(User loggedUser, String plate) {
        UserSqlDao dao = new UserSqlDao();

        try {
            return dao.registeredVehicle(loggedUser, plate);
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error en la base de datos.";
        }
    }

    public String isBooked(User loggedUser, String plate) {
        UserSqlDao dao = new UserSqlDao();

        try {
            return dao.isBooked(loggedUser, plate);
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error en la base de datos.";
        }
    }

    public String placesAvailable(User loggedUser, String plate, String vehicle) {
        UserSqlDao dao = new UserSqlDao();

        try {
            return dao.placesAvailable(loggedUser, plate, vehicle);
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error en la base de datos.";
        }
    }

    public boolean sameTypeVehicle(User loggedUser, String plate, String vehicle) {
        UserSqlDao dao = new UserSqlDao();

        try {
            return dao.sameTypeVehicle(loggedUser, plate, vehicle);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
