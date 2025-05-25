package business.model;

import java.time.LocalDateTime;

/**
 * The type Vehicle entry.
 */
public class VehicleEntry {
    private final String plate;
    private final LocalDateTime entryTime;

    /**
     * Instantiates a new Vehicle entry.
     *
     * @param plate     the plate
     * @param entryTime the entry time
     */
    public VehicleEntry(String plate, LocalDateTime entryTime) {
        this.plate = plate;
        this.entryTime = entryTime;
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
     * Gets entry time.
     *
     * @return the entry time
     */
    public LocalDateTime getEntryTime() {
        return entryTime;
    }
}