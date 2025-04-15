package business.model;

public class Vehicle {
    private String plate;
    private String brand;
    private String model;
    private String color;
    private String type;
    private User user;

    public Vehicle(String plate, String brand, String model, String color, User user, String type) {
        this.plate = plate;
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.user = user;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public User getVehicleOwner() {
        return user;
    }

    public String getPlate() {
        return plate;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }

    public User getUser() {
        return user;
    }
}
