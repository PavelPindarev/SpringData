package entities.vehicle;

import javax.persistence.Entity;

@Entity
public class Car extends Vehicle{
    private final static String DEFAULT_TYPE = "CAR";

    private int doors;

    public Car (int doors) {
        super(DEFAULT_TYPE);
        this.doors = doors;
    }

    public Car() {

    }

    public int getDoors() {
        return doors;
    }

    public void setDoors(int doors) {
        this.doors = doors;
    }
}
