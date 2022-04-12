package com.example.xmlexercise.entity.user;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UsersImportDTO {

    @XmlElement(name = "user")
    private List<UserImportDTO> userImportDTOs;

    public UsersImportDTO() {
    }

    public List<UserImportDTO> getUserImportDTOs() {
        return userImportDTOs;
    }

    public void setUserImportDTOs(List<UserImportDTO> userImportDTOs) {
        this.userImportDTOs = userImportDTOs;
    }
}
