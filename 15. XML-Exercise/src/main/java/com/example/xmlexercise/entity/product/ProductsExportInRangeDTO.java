package com.example.xmlexercise.entity.product;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "products")
public class ProductsExportInRangeDTO {

    @XmlElement(name = "product")
    private List<ProductInRange> productInRangeList;

    public ProductsExportInRangeDTO() {
    }

    public ProductsExportInRangeDTO(List<ProductInRange> productInRangeList) {
        this.productInRangeList = productInRangeList;
    }

    public List<ProductInRange> getProductInRangeList() {
        return productInRangeList;
    }


}
