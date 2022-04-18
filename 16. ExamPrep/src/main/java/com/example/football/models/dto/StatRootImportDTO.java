package com.example.football.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "stats")
public class StatRootImportDTO {

    @XmlElement(name = "stat")
    private List<StatImportDTO> stats;

    public StatRootImportDTO() {
    }

    public List<StatImportDTO> getStats() {
        return stats;
    }
}
