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

    public String plateInside(User loggedUser, String plate) {
        UserSqlDao dao = new UserSqlDao();

        try {
            return dao.plateInside(loggedUser, plate);
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error en la base de datos.";
        }
    }

    public String updatePlate(User loggedUser, String plate) {
        UserSqlDao dao = new UserSqlDao();

        try {
            return dao.updatePlate(loggedUser, plate);
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error en la base de datos.";
        }
    }
}
