package entities.vehicle;

import javax.persistence.Entity;

@Entity
public class Truck extends Vehicle{
    private final static String DEFAULT_TYPE = "CAR";

    private int loadCapacity;

    public Truck(int loadCapacity){
        super(DEFAULT_TYPE);
        this.loadCapacity = loadCapacity;
    }

    public Truck(){

    }

    public int getLoadCapacity() {
        return loadCapacity;
    }

    public void setLoadCapacity(int loadCapacity) {
        this.loadCapacity = loadCapacity;
    }
}
