package business.model;

import java.time.LocalDateTime;

/**
 * The type Vehicle event.
 */
public class VehicleEvent {
    private final String plate;
    private final String action; // "entry" or "leave"
    private final LocalDateTime timestamp;

    /**
     * Instantiates a new Vehicle event.
     *
     * @param plate     the plate
     * @param action    the action
     * @param timestamp the timestamp
     */
    public VehicleEvent(String plate, String action, LocalDateTime timestamp) {
        this.plate = plate;
        this.action = action;
        this.timestamp = timestamp;
    }

    /**
     * Gets plate.
     *
     * @return the plate
     */
    public String getPlate() {
        return plate;
    }

    /**
     * Gets action.
     *
     * @return the action
     */
    public String getAction() {
        return action;
    }

    /**
     * Gets timestamp.
     *
     * @return the timestamp
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}