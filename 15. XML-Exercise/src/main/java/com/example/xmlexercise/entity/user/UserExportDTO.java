package com.example.xmlexercise.entity.user;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "users")
public class UserExportDTO {

    @XmlElement(name = "user")
    private List<UserAttributesDTO> users;

    public UserExportDTO() {
    }

    public UserExportDTO(List<UserAttributesDTO> users) {
        this.users = users;
    }

    public List<UserAttributesDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserAttributesDTO> users) {
        this.users = users;
    }
}
