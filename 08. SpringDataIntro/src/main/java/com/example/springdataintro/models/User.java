package com.example.springdataintro.models;

import javax.persistence.*;
import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private Integer age;

    @OneToMany(mappedBy = "user")
    private Set<Account> accounts;

    public User(String username, Integer age) {
        this.username = username;
        this.age = age;
    }

    public User() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Set<Account> getAccounts() {
        return Collections.unmodifiableSet(accounts);
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }
}
