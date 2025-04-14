package business.model;

public class Slot {
    private int idSlot;
    private int floor;
    private String vehicle;
    private int availabilityState;
    private boolean booked; // New attribute
    private String vehiclePlate;
    private Vehicle vehicleObject;

    public Slot(String vehicle, int idSlot, int floor) {
        this.vehicle = vehicle;
        this.idSlot = idSlot;
        this.floor = floor;
        this.availabilityState = 0; // Default state
        this.booked = false;
    }

    public Slot(String vehiclePlate, int idSlot, int isOccupeid , int floor, boolean booked) {
        this.vehiclePlate = vehiclePlate;
        this.idSlot = idSlot;
        this.floor = floor;
        this.availabilityState = isOccupeid;
        this.booked = booked;
    }

    public Slot(String vehicle, int idSlot, int floor, int occupeid) {
        this.vehicle = vehicle;
        this.idSlot = idSlot;
        this.floor = floor;
        this.availabilityState = occupeid; // Default state
        this.booked = false;
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
    public boolean getBooked() {
        return booked;
    }

    public String getVehiclePlate() {
        return vehiclePlate;
    }

    public void setVehiclePlate(String vehiclePlate) {
        this.vehiclePlate = vehiclePlate;
    }

    // New setter for booked
    public void setBooked(boolean booked) {
        this.booked = booked;
    }
    public void setAvailabilityState(int availabilityState) {
        this.availabilityState = availabilityState;
    }

    public Vehicle getVehicleObject() {
        return vehicleObject;
    }

    public void setVehicleObject(Vehicle vehicleObject) {
        this.vehicleObject = vehicleObject;
    }
}