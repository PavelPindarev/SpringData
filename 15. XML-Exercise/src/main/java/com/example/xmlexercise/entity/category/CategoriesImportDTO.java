package com.example.xmlexercise.entity.category;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoriesImportDTO implements Serializable {

    @XmlElement(name = "category")
    private List<CategoryImportDTO> categories;

    public CategoriesImportDTO() {
    }

    public List<CategoryImportDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryImportDTO> categories) {
        this.categories = categories;
    }
}
