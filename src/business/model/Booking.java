package business.model;

public class Booking {
    private int licensePlate;
    private String vehicleType;

    public Booking(int licensePlate, String vehicleType) {
        this.licensePlate = licensePlate;
        this.vehicleType = vehicleType;
    }
    public int getLicensePlate() {
        return licensePlate;
    }
    public String getVehicleType() {
        return vehicleType;
    }
}
