package business.model;

import java.util.List;

public class Parking {
    private int id;
    private String name;
    private String location;
    private List<Slot> slots;

    public Parking(int id, String name, String location, List<Slot> slots) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.slots = slots;
    }
}
