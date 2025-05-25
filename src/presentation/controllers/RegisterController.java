package presentation.controllers;

import business.LoginManager;
import business.RegisterManager;
import business.exceptions.RegisterException;

import java.sql.SQLException;

/**
 * The type Register controller.
 */
public class RegisterController {
    private RegisterManager registerManager;

    /**
     * Instantiates a new Register controller.
     *
     * @throws SQLException the sql exception
     */
    public RegisterController() throws SQLException{
        registerManager = new RegisterManager();
    }

    /**
     * Register string.
     *
     * @param name     the name
     * @param password the password
     * @param email    the email
     * @return the string
     * @throws RegisterException the register exception
     */
    public String register(String name, String password, String email) throws RegisterException {
        return registerManager.register(name, password, email);
    }
}
