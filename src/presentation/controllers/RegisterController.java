package presentation.controllers;

import business.LoginManager;
import business.RegisterManager;
import business.exceptions.RegisterException;

import java.sql.SQLException;

public class RegisterController {
    private RegisterManager registerManager;

    public RegisterController() throws SQLException{
        registerManager = new RegisterManager();
    }

    public String register(String name, String password, String email) throws RegisterException {
        return registerManager.register(name, password, email);
    }
}
