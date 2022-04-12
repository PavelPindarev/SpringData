package com.example.xmlexercise.entity.user;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "users")
public class UsersExportAndCountDTO {

    @XmlAttribute
    private int count;

    @XmlElement(name = "user")
    private List<UserInfoExportDTO> userInfoExportDTOList;

    public UsersExportAndCountDTO() {
    }

    public UsersExportAndCountDTO(int count, List<UserInfoExportDTO> userInfoExportDTOList) {
        this.count = count;
        this.userInfoExportDTOList = userInfoExportDTOList;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<UserInfoExportDTO> getUserInfoExportDTOList() {
        return userInfoExportDTOList;
    }

    public void setUserInfoExportDTOList(List<UserInfoExportDTO> userInfoExportDTOList) {
        this.userInfoExportDTOList = userInfoExportDTOList;
    }
}
