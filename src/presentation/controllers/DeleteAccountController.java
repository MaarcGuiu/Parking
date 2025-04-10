package presentation.controllers;

import business.DeleteAccountManager;

public class DeleteAccountController {
    private DeleteAccountManager deleteAccountManager;

    public DeleteAccountController() {
        deleteAccountManager = new DeleteAccountManager();
    }

    public String deleteAccount(String emailOrName, String password) {
        return deleteAccountManager.deleteAccount(emailOrName, password);
    }
}
