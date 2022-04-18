package com.example.xmlexercise.entity.user;

import com.example.xmlexercise.entity.product.ProductExportDTO;
import com.example.xmlexercise.entity.product.ProductImportDTO;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "user")
public class UserInfoExportDTO {

    @XmlAttribute(name = "first-name")
    private String firstName;

    @XmlAttribute(name = "last-name")
    private String lastName;

    @XmlAttribute
    private int age;

    @XmlElementWrapper(name = "sold-products")
    @XmlElement(name = "product")
    private List<ProductExportDTO> soldProducts;

    public UserInfoExportDTO() {
    }

    public UserInfoExportDTO(String firstName, String lastName, int age, List<ProductExportDTO> soldProducts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.soldProducts = soldProducts;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<ProductExportDTO> getProductExportDTOS() {
        return soldProducts;
    }

    public void setProductExportDTOS(List<ProductExportDTO> productExportDTOS) {
        this.soldProducts = productExportDTOS;
    }
}
