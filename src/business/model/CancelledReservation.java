package business.model;

public class CancelledReservation {
    private int id;
    private User user;
    private Slot slot;
    private Vehicle vehicle;

    public CancelledReservation(int id, User user, Slot slot, Vehicle vehicle) {
        this.id = id;
        this.user = user;
        this.slot = slot;
        this.vehicle = vehicle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUserId(User user) {
        this.user = user;
    }

    public Slot getSlot() {
        return slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}

