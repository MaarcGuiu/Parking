package presentation.controllers;

import business.DeleteAccountManager;
import business.LoginManager;

public class DeleteAccountController {
    private DeleteAccountManager deleteAccountManager;

    public DeleteAccountController() {
        deleteAccountManager = new DeleteAccountManager();
    }

    public String deleteAccount(String emailOrName, String password) {
        return deleteAccountManager.deleteAccount(emailOrName, password);
    }
}
