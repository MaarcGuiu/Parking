package presentation.controllers;

import business.LoginManager;
import business.RegisterManager;

public class RegisterController {
    private RegisterManager registerManager;

    public RegisterController() {
        registerManager = new RegisterManager();
    }

    public String register(String name, String password, String email) {
        return registerManager.register(name, password, email);
    }
}
