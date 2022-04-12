package com.example.football.models.dto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class TownImportDTO {

    @Size(min = 2)
    private String name;

    @Positive
    private int population;

    @Size(min = 10)
    private String travelGuide;

    public TownImportDTO() {
    }

    public String getName() {
        return name;
    }

    public int getPopulation() {
        return population;
    }

    public String getTravelGuide() {
        return travelGuide;
    }

}
