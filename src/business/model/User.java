package business.model;

/**
 * The type User.
 */
public class User {
    private int id;
    private String username;
    private String password;
    private String email;

    /**
     * Instantiates a new User.
     *
     * @param id       the id
     * @param username the username
     * @param password the password
     * @param email    the email
     */
    public User(int id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    /**
     * Gets user name.
     *
     * @return the user name
     */
    public String getUserName() {
        return this.username;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() { return this.password; }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() { return this.email; }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() { return this.id; }
}
