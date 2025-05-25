package presentation.controllers;

import business.DeleteAccountManager;

import java.sql.SQLException;

/**
 * The type Delete account controller.
 */
public class DeleteAccountController {
    private DeleteAccountManager deleteAccountManager;

    /**
     * Instantiates a new Delete account controller.
     *
     * @throws SQLException the sql exception
     */
    public DeleteAccountController() throws SQLException {
        deleteAccountManager = new DeleteAccountManager();
    }

    /**
     * Delete account string.
     *
     * @param emailOrName the email or name
     * @param password    the password
     * @return the string
     * @throws SQLException             the sql exception
     * @throws IllegalArgumentException the illegal argument exception
     */
    public String deleteAccount(String emailOrName, String password) throws SQLException, IllegalArgumentException {
        return deleteAccountManager.deleteAccount(emailOrName, password);
    }
}
