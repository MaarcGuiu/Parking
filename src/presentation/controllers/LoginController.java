package presentation.controllers;

import business.LoginManager;
import business.model.CancelledReservation;
import business.model.Slot;
import business.model.User;

import java.sql.SQLException;
import java.util.List;

public class LoginController {
    private LoginManager loginManager;

    public LoginController() throws SQLException {
        loginManager = new LoginManager();
    }

    public String login(String emailOrName, String password) throws SQLException {
        return loginManager.login(emailOrName, password);
    }

    public User getUser(String emailOrName) throws SQLException {
        return loginManager.getUser(emailOrName);
    }

    public List<CancelledReservation> getCancelledReservationsByUserId(int userId) throws SQLException {
        return loginManager.getCancelledReservationsByUserId(userId);
    }

    public void deleteCancelledReservationById(int id) throws SQLException {
        loginManager.deleteCancelledReservationById(id);
    }

    public Slot getSlotByPlate(String vehiclePlate) throws SQLException {
        return loginManager.getSlotByPlate(vehiclePlate);
    }
}
