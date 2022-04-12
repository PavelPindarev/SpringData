package com.example.xmlexercise.entity.product;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
public class ProductExportDTO {
    private String name;

    private BigDecimal price;
}
