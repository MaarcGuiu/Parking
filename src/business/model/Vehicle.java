package business.model;

public class Vehicle {
    private String plate;
    private String brand;
    private String model;
    private String color;
    private User user;

    public Vehicle(String plate, String brand, String model, String color, User user) {
        this.plate = plate;
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.user = user;
    }
}
