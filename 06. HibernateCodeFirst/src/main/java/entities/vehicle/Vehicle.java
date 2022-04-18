package entities.vehicle;

import javax.persistence.*;

@Entity(name = "vehicles")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public abstract class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Basic
    @Column(updatable = false, insertable = false)
    private String type;

    protected Vehicle() {
    }

    protected Vehicle(String type) {
        this.type = type;
    }
}
