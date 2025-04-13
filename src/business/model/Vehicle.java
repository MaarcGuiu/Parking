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
    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
