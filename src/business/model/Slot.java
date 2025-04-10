package business.model;

public class Slot {
    private int idSlot;
    private int floor;
    private String vehicle;
    private int availabilityState;
    private String booked; // New attribute
    private String vehiclePlate;

    public Slot(String vehicle, int idSlot, int floor) {
        this.vehicle = vehicle;
        this.idSlot = idSlot;
        this.floor = floor;
        this.availabilityState = 0; // Default state
        this.booked = "notBooked";
    }

    public Slot(String vehicle, int idSlot, int floor, int occupeid) {
        this.vehicle = vehicle;
        this.idSlot = idSlot;
        this.floor = floor;
        this.availabilityState = occupeid; // Default state
        this.booked = "notBooked";
    }

    public int getIdSlot() {
        return idSlot;
    }

    public int getAvailabilityState() {
        return availabilityState;
    }

    public int getFloor() {
        return floor;
    }

    public String getVehicle() {
        return vehicle;
    }

    // New getter for booked
    public String getBooked() {
        return booked;
    }

    public String getVehiclePlate() {
        return vehiclePlate;
    }

    public void setVehiclePlate(String vehiclePlate) {
        this.vehiclePlate = vehiclePlate;
    }

    // New setter for booked
    public void setBooked(String booked) {
        this.booked = booked;
    }
    public void setAvailabilityState(int availabilityState) {
        this.availabilityState = availabilityState;
    }

}