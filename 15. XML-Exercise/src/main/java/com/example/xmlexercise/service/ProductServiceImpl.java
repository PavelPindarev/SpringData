package com.example.xmlexercise.service;

import com.example.xmlexercise.entity.product.Product;
import com.example.xmlexercise.entity.product.ProductInRange;
import com.example.xmlexercise.entity.product.ProductsExportInRangeDTO;
import com.example.xmlexercise.entity.user.User;
import com.example.xmlexercise.repository.ProductRepository;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper mapper;
    private TypeMap<Product, ProductInRange> productDTOMapping;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;

        this.mapper = new ModelMapper();

        Converter<User, String> userToUserFullName =
                context -> context.getSource() == null ? null : context.getSource().getFullName();

        TypeMap<Product, ProductInRange> typeMap =
                this.mapper.createTypeMap(Product.class, ProductInRange.class);

        this.productDTOMapping = typeMap.addMappings(map ->
                map.using(userToUserFullName)
                        .map(Product::getSeller, ProductInRange::setSeller));
    }


    @Override
    public ProductsExportInRangeDTO getProductsInRange(float from, float to) {
        BigDecimal RangeFrom = BigDecimal.valueOf(from);
        BigDecimal RangeTo = BigDecimal.valueOf(to);

        List<Product> products = this.productRepository
                .findProductsByPriceBetweenAndBuyerIsNullOrderByPriceAsc(RangeFrom, RangeTo);

        List<ProductInRange> productsInRange = products
                .stream()
                .map(product -> productDTOMapping.map(product))
                .collect(Collectors.toList());

        return new ProductsExportInRangeDTO(productsInRange);
    }
}
