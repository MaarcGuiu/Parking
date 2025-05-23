package business.model;

import java.time.LocalDateTime;

public class VehicleEvent {
    private final String plate;
    private final String action; // "entry" or "leave"
    private final LocalDateTime timestamp;

    public VehicleEvent(String plate, String action, LocalDateTime timestamp) {
        this.plate = plate;
        this.action = action;
        this.timestamp = timestamp;
    }

    public String getPlate() {
        return plate;
    }

    public String getAction() {
        return action;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}