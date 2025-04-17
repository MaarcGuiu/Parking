package presentation.controllers;

import business.LoginManager;
import business.model.CancelledReservation;
import business.model.Slot;
import business.model.User;

import java.util.List;

public class LoginController {
    private LoginManager loginManager;

    public LoginController() {
        loginManager = new LoginManager();
    }

    public String login(String emailOrName, String password) {
        return loginManager.login(emailOrName, password);
    }

    public User getUser(String emailOrName) {
        return loginManager.getUser(emailOrName);
    }

    public List<CancelledReservation> getCancelledReservationsByUserId(int userId) {
        return loginManager.getCancelledReservationsByUserId(userId);
    }

    public void deleteCancelledReservationById(int id) {
        loginManager.deleteCancelledReservationById(id);
    }

    public Slot getSlotByPlate(String vehiclePlate) {
        return loginManager.getSlotByPlate(vehiclePlate);
    }
}
