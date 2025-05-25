package presentation.controllers;

import business.LoginManager;
import business.model.CancelledReservation;
import business.model.Slot;
import business.model.User;

import java.sql.SQLException;
import java.util.List;

/**
 * The type Login controller.
 */
public class LoginController {
    private LoginManager loginManager;

    /**
     * Instantiates a new Login controller.
     *
     * @throws SQLException the sql exception
     */
    public LoginController() throws SQLException {
        loginManager = new LoginManager();
    }

    /**
     * Login string.
     *
     * @param emailOrName the email or name
     * @param password    the password
     * @return the string
     * @throws SQLException the sql exception
     */
    public String login(String emailOrName, String password) throws SQLException {
        return loginManager.login(emailOrName, password);
    }

    /**
     * Gets user.
     *
     * @param emailOrName the email or name
     * @return the user
     * @throws SQLException the sql exception
     */
    public User getUser(String emailOrName) throws SQLException {
        return loginManager.getUser(emailOrName);
    }

    /**
     * Gets cancelled reservations by user id.
     *
     * @param userId the user id
     * @return the cancelled reservations by user id
     * @throws SQLException the sql exception
     */
    public List<CancelledReservation> getCancelledReservationsByUserId(int userId) throws SQLException {
        return loginManager.getCancelledReservationsByUserId(userId);
    }

    /**
     * Delete cancelled reservation by id.
     *
     * @param id the id
     * @throws SQLException the sql exception
     */
    public void deleteCancelledReservationById(int id) throws SQLException {
        loginManager.deleteCancelledReservationById(id);
    }

    /**
     * Gets slot by plate.
     *
     * @param vehiclePlate the vehicle plate
     * @return the slot by plate
     * @throws SQLException the sql exception
     */
    public Slot getSlotByPlate(String vehiclePlate) throws SQLException {
        return loginManager.getSlotByPlate(vehiclePlate);
    }
}
