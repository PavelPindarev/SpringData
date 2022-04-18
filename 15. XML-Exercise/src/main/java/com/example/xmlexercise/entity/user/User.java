package com.example.xmlexercise.entity.user;

import com.example.xmlexercise.entity.product.Product;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(nullable = false, name = "last_name")
    private String lastName;

    private Integer age;

    @OneToMany(mappedBy = "buyer", targetEntity = Product.class)
    private List<Product> boughtProducts;

    @OneToMany(mappedBy = "seller", targetEntity = Product.class)
    private List<Product> soldProducts;

    @ManyToMany
    private Set<User> friends;

    public User() {
        this.boughtProducts = new ArrayList<>();
        this.soldProducts = new ArrayList<>();
        this.friends = new HashSet<>();
    }

    public User(String firstName, String lastName, Integer age) {
        this();

        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<Product> getBoughtProducts() {
        return boughtProducts;
    }

    public void setBoughtProducts(List<Product> boughtProducts) {
        this.boughtProducts = boughtProducts;
    }

    public List<Product> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(List<Product> soldProducts) {
        this.soldProducts = soldProducts;
    }

    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    public String getFullName() {
        if (firstName == null || firstName.trim().isEmpty()) {
            return lastName;
        } else {
            return firstName + " " + lastName;
        }
    }
}
