package business.model;

public class Config {
    private int port;
    private String ipAddress;
    private String name;
    private String username;
    private String password;
    private String adminPassword;
    private int duration;

    // Constructor
    public Config(int port, String ipAddress, String name, String username, String password, String adminPassword, int duration) {
        this.port = port;
        this.ipAddress = ipAddress;
        this.name = name;
        this.username = username;
        this.password = password;
        this.adminPassword = adminPassword;
        this.duration = duration;
    }
    public int getPort() {
        return port;
    }
    public String getUserName() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getAdminPassword() {
        return adminPassword;
    }
    public int getDuration() {
        return duration;
    }

}
