package presentation.controllers;

import business.DeleteAccountManager;

import java.sql.SQLException;

public class DeleteAccountController {
    private DeleteAccountManager deleteAccountManager;

    public DeleteAccountController() throws SQLException {
        deleteAccountManager = new DeleteAccountManager();
    }

    public String deleteAccount(String emailOrName, String password) throws SQLException, IllegalArgumentException {
        return deleteAccountManager.deleteAccount(emailOrName, password);
    }
}
