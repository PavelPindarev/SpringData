package com.example.gamestore.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;

    @Column(name = "trailer_id", nullable = false)
    private String trailerId;

    @Column(name = "thumbnail_url", nullable = false)
    private String thumbnailUrl;

    @Column(nullable = false)
    private double size;

    @Column(nullable = false)
    private BigDecimal price;

    private String description;

    @Column(nullable = false)
    private LocalDate releasedDate;

    public Game() {}

    public Game(String title, String trailerId, String thumbnailUrl, double size,
                BigDecimal price, String description, LocalDate releasedDate) {
        this.title = title;
        this.trailerId = trailerId;
        this.thumbnailUrl = thumbnailUrl;
        this.size = size;
        this.price = price;
        this.description = description;
        this.releasedDate = releasedDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTrailerId() {
        return trailerId;
    }

    public void setTrailerId(String trailerId) {
        this.trailerId = trailerId;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleasedDate() {
        return releasedDate;
    }

    public void setReleasedDate(LocalDate releasedDate) {
        this.releasedDate = releasedDate;
    }
}
