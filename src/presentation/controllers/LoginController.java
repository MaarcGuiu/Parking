package presentation.controllers;

import business.LoginManager;

public class LoginController {
    private LoginManager loginManager;

    public LoginController() {
        loginManager = new LoginManager();
    }

    public String login(String emailOrName, String password) {
        return loginManager.login(emailOrName, password);
    }
}
