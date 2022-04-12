package com.example.xmlexercise.entity.category;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "categories")
public class CategoriesExportDTO {

    @XmlElement(name = "category")
    private List<CategoryStatsDTO> categoryStatsDTOList;

    public CategoriesExportDTO() {
    }

    public CategoriesExportDTO(List<CategoryStatsDTO> categoryStatsDTOList) {
        this.categoryStatsDTOList = categoryStatsDTOList;
    }

    public List<CategoryStatsDTO> getCategoryStatsDTOList() {
        return categoryStatsDTOList;
    }

    public void setCategoryStatsDTOList(List<CategoryStatsDTO> categoryStatsDTOList) {
        this.categoryStatsDTOList = categoryStatsDTOList;
    }
}
