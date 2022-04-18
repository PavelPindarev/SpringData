package com.example.football.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "players")
public class PlayerImportRootDTO {

    @XmlElement(name = "player")
    List<PlayerImportDTO> players;

    public PlayerImportRootDTO() {
    }

    public List<PlayerImportDTO> getPlayers() {
        return players;
    }
}
