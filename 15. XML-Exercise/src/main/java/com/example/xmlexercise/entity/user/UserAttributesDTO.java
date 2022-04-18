package com.example.xmlexercise.entity.user;


import com.example.xmlexercise.entity.product.ProductsWithBuyerDTO;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "user")
public class UserAttributesDTO {

    @XmlAttribute(name = "first-name")
    private String firstName;

    @XmlAttribute(name = "last-name")
    private String lastName;

    @XmlElementWrapper(name = "sold-products")
    @XmlElement(name = "product")
    private List<ProductsWithBuyerDTO> soldProducts;

    public UserAttributesDTO() {
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

    public List<ProductsWithBuyerDTO> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(List<ProductsWithBuyerDTO> soldProducts) {
        this.soldProducts = soldProducts;
    }
}
