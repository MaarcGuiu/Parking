package presentation.controllers;

import business.Managers.LoginManager;
import business.model.User;

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
}
