package business.model;

public class Slot {
    private int idSlot;
    private int floor;
    private String vehicle;
    private String availabilityState;
    private String booked; // New attribute

    public Slot(String vehicle, int idSlot, int floor) {
        this.vehicle = vehicle;
        this.idSlot = idSlot;
        this.floor = floor;
        this.availabilityState = "available"; // Default state
        this.booked = "notBooked";
    }

    public int getIdSlot() {
        return idSlot;
    }

    public String getAvailabilityState() {
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

    // New setter for booked
    public void setBooked(String booked) {
        this.booked = booked;
    }
    public void setAvailabilityState(String availabilityState) {
        this.availabilityState = availabilityState;
    }

}
