package business.model;

public class User {
    private String userName;
    private String email;
    private String password;

    public User(int id, String name, String email, String password) {
        this.userName = name;
        this.email = email;
        this.password = password;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String name) {
        this.userName = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}