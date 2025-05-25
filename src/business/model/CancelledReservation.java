package business.model;

/**
 * The type Cancelled reservation.
 */
public class CancelledReservation {
    private int id;
    private User user;
    private Slot slot;
    private Vehicle vehicle;

    /**
     * Instantiates a new Cancelled reservation.
     *
     * @param id      the id
     * @param user    the user
     * @param slot    the slot
     * @param vehicle the vehicle
     */
    public CancelledReservation(int id, User user, Slot slot, Vehicle vehicle) {
        this.id = id;
        this.user = user;
        this.slot = slot;
        this.vehicle = vehicle;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets user id.
     *
     * @param user the user
     */
    public void setUserId(User user) {
        this.user = user;
    }

    /**
     * Gets slot.
     *
     * @return the slot
     */
    public Slot getSlot() {
        return slot;
    }

    /**
     * Sets slot.
     *
     * @param slot the slot
     */
    public void setSlot(Slot slot) {
        this.slot = slot;
    }

    /**
     * Gets vehicle.
     *
     * @return the vehicle
     */
    public Vehicle getVehicle() {
        return vehicle;
    }

    /**
     * Sets vehicle.
     *
     * @param vehicle the vehicle
     */
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}

