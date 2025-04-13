package business.model;

public class Slot {
    private int idSlot;
    private int floor;
    private int availabilityState;
    private String booked; // New attribute
    private String vehiclePlate;
    private String type;

    public Slot(int idSlot, int floor, String type,int occupied, String vehicle) {
        this.vehiclePlate = vehicle;
        this.idSlot = idSlot;
        this.type = type;
        this.floor = floor;
        this.availabilityState = occupied; // Default state
        this.booked = "notBooked";
    }

    public String getType() {
        return type;
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

    public String getBooked() {
        return booked;
    }

    public String getVehiclePlate() {
        return vehiclePlate;
    }

    public void setVehiclePlate(String vehiclePlate) {
        this.vehiclePlate = vehiclePlate;
    }

    public void setBooked(String booked) {
        this.booked = booked;
    }
    public void setAvailabilityState(int availabilityState) {
        this.availabilityState = availabilityState;
    }

}