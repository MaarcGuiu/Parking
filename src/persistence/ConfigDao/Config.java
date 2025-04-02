package persistence.ConfigDao;

public class Config {
    private int dbPort;
    private String serverIp;
    private String dbName;
    private String user;
    private String pwd;
    private String adminPwq;
    private int vehicleTime;

    public int getDbPort() {
        return dbPort;
    }

    public String getServerIp() {
        return serverIp;
    }

    public String getDbName() {
        return dbName;
    }

    public String getUser() {
        return user;
    }

    public String getPwd() {
        return pwd;
    }

    public String getAdminPwq() {
        return adminPwq;
    }

    public int getVehicleTime() {
        return vehicleTime;
    }
}
