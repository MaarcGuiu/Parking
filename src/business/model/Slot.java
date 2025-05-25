package business.model;

/**
 * The type Slot.
 */
public class Slot {
    private int idSlot;
    private int floor;
    private String vehicle;
    private int availabilityState;
    private boolean booked; // New attribute
    private String vehiclePlate;
    private Vehicle vehicleObject;

    /**
     * Instantiates a new Slot.
     *
     * @param vehicle the vehicle
     * @param idSlot  the id slot
     * @param floor   the floor
     */
    public Slot(String vehicle, int idSlot, int floor) {
        this.vehicle = vehicle;
        this.idSlot = idSlot;
        this.floor = floor;
        this.availabilityState = 0; // Default state
        this.booked = false;
    }

    /**
     * Instantiates a new Slot.
     *
     * @param vehiclePlate the vehicle plate
     * @param idSlot       the id slot
     * @param isOccupeid   the is occupeid
     * @param floor        the floor
     * @param booked       the booked
     * @param vehicleType  the vehicle type
     */
    public Slot(String vehiclePlate, int idSlot, int isOccupeid , int floor, boolean booked, String vehicleType) {
        this.vehiclePlate = vehiclePlate;
        this.idSlot = idSlot;
        this.floor = floor;
        this.availabilityState = isOccupeid;
        this.booked = booked;
        this.vehicle = vehicleType;
    }

    /**
     * Instantiates a new Slot.
     *
     * @param vehicle  the vehicle
     * @param idSlot   the id slot
     * @param floor    the floor
     * @param occupeid the occupeid
     */
    public Slot(String vehicle, int idSlot, int floor, int occupeid) {
        this.vehicle = vehicle;
        this.idSlot = idSlot;
        this.floor = floor;
        this.availabilityState = occupeid; // Default state
        this.booked = false;
    }

    /**
     * Instantiates a new Slot.
     *
     * @param slotNumber   the slot number
     * @param id           the id
     * @param isOccupied   the is occupied
     * @param plant        the plant
     * @param booked       the booked
     * @param vehicleType  the vehicle type
     * @param vehiclePlate the vehicle plate
     */
    public Slot(String slotNumber, int id, int isOccupied, int plant, boolean booked, String vehicleType, String vehiclePlate) {
        this.idSlot = id;
        this.availabilityState = isOccupied;
        this.booked = booked;
        this.vehiclePlate = vehiclePlate;
        this.vehicle = vehicleType;
        this.vehicleObject = null;
        this.floor = plant;
    }

    /**
     * Gets id slot.
     *
     * @return the id slot
     */
    public int getIdSlot() {
        return idSlot;
    }

    /**
     * Gets availability state.
     *
     * @return the availability state
     */
    public int getAvailabilityState() {
        return availabilityState;
    }

    /**
     * Gets floor.
     *
     * @return the floor
     */
    public int getFloor() {
        return floor;
    }

    /**
     * Gets vehicle.
     *
     * @return the vehicle
     */
    public String getVehicle() {
        return vehicle;
    }

    /**
     * Gets booked.
     *
     * @return the booked
     */
// New getter for booked
    public boolean getBooked() {
        return booked;
    }

    /**
     * Gets vehicle plate.
     *
     * @return the vehicle plate
     */
    public String getVehiclePlate() {
        return vehiclePlate;
    }

    /**
     * Sets vehicle plate.
     *
     * @param vehiclePlate the vehicle plate
     */
    public void setVehiclePlate(String vehiclePlate) {
        this.vehiclePlate = vehiclePlate;
    }

    /**
     * Sets booked.
     *
     * @param booked the booked
     */
// New setter for booked
    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    /**
     * Sets availability state.
     *
     * @param availabilityState the availability state
     */
    public void setAvailabilityState(int availabilityState) {
        this.availabilityState = availabilityState;
    }

    /**
     * Gets vehicle object.
     *
     * @return the vehicle object
     */
    public Vehicle getVehicleObject() {
        return vehicleObject;
    }

    /**
     * Sets vehicle object.
     *
     * @param vehicleObject the vehicle object
     */
    public void setVehicleObject(Vehicle vehicleObject) {
        this.vehicleObject = vehicleObject;
    }

    /**
     * Sets id.
     *
     * @param idSlot the id slot
     */
    public void setId(int idSlot) {
        this.idSlot = idSlot;
    }
}