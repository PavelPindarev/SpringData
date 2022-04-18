package _02.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private int quantity;

    private BigDecimal price;

    @OneToMany(mappedBy = "product", targetEntity = Sale.class)
    private Set<Sale> sales;
}
