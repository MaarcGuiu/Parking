package business.model;

/**
 * The type Vehicle.
 */
public class Vehicle {
    private String plate;
    private String brand;
    private String model;
    private String color;
    private String type;
    private User user;

    /**
     * Instantiates a new Vehicle.
     *
     * @param plate the plate
     * @param brand the brand
     * @param model the model
     * @param color the color
     * @param user  the user
     * @param type  the type
     */
    public Vehicle(String plate, String brand, String model, String color, User user, String type) {
        this.plate = plate;
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.user = user;
        this.type = type;
    }

    /**
     * Instantiates a new Vehicle.
     *
     * @param plate the plate
     * @param brand the brand
     * @param model the model
     * @param color the color
     * @param type  the type
     */
    public Vehicle(String plate, String brand, String model, String color,String type) {
        this.plate = plate;
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.type = type;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Gets vehicle owner.
     *
     * @return the vehicle owner
     */
    public User getVehicleOwner() {
        return user;
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
     * Gets brand.
     *
     * @return the brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Gets model.
     *
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * Gets color.
     *
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }
}
