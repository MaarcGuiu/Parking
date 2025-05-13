package business;

import business.model.User;
import persistence.UserSqlDao;

import java.sql.SQLException;

public class LeaveManager {
    public String userPlate(User loggedUser, String plate) {
        UserSqlDao dao = new UserSqlDao();

        try {
            return dao.userPlate(loggedUser, plate);
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error en la base de datos.";
        }
    }

    public String isVehicleInside(User loggedUser, String plate) {
        UserSqlDao dao = new UserSqlDao();

        try {
            if (dao.isVehicleInside(plate)) {
                return "success";
            } else {
                return "The vehicle is not in the parking lot.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error en la base de datos.";
        }
    }

    public String updatePlate(String plate) {
        UserSqlDao dao = new UserSqlDao();

        try {
            return dao.updatePlate(plate);
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error en la base de datos.";
        }
    }
}
