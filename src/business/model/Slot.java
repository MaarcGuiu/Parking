package business.model;

public class Slot {
    private int id;
    private Parking parking;
    private String slotNumber;
    private boolean isOccupied;
    private String vehiclePlate;

    public Slot(int id, Parking parking, String slotNumber, boolean isOccupied, String vehiclePlate) {
        this.id = id;
        this.parking = parking;
        this.slotNumber = slotNumber;
        this.isOccupied = isOccupied;
        this.vehiclePlate = vehiclePlate;
    }
}
