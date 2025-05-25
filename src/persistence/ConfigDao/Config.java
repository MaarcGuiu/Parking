package persistence.ConfigDao;

/**
 * The type Config.
 */
public class Config {
    private int dbPort;
    private String serverIp;
    private String dbName;
    private String user;
    private String pwd;
    private String adminPwd;
    private int vehicleTime;

    /**
     * Gets db port.
     *
     * @return the db port
     */
    public int getDbPort() {
        return dbPort;
    }

    /**
     * Gets server ip.
     *
     * @return the server ip
     */
    public String getServerIp() {
        return serverIp;
    }

    /**
     * Gets db name.
     *
     * @return the db name
     */
    public String getDbName() {
        return dbName;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * Gets pwd.
     *
     * @return the pwd
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * Gets admin pwd.
     *
     * @return the admin pwd
     */
    public String getAdminPwd() {
        return adminPwd;
    }

    /**
     * Gets vehicle time.
     *
     * @return the vehicle time
     */
    public int getVehicleTime() {
        return vehicleTime;
    }
}
