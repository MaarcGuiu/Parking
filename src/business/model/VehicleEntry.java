package business.model;

import java.time.LocalDateTime;

public class VehicleEntry {
    private final String plate;
    private final LocalDateTime entryTime;

    public VehicleEntry(String plate, LocalDateTime entryTime) {
        this.plate = plate;
        this.entryTime = entryTime;
    }

    public String getPlate() {
        return plate;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }
}