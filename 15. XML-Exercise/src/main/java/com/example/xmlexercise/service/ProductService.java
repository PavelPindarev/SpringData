package com.example.xmlexercise.service;

import com.example.xmlexercise.entity.product.ProductsExportInRangeDTO;

public interface ProductService {
    ProductsExportInRangeDTO getProductsInRange(float from, float to);
}
