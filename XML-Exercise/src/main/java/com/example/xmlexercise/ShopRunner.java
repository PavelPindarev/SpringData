package com.example.xmlexercise;

import com.example.xmlexercise.entity.category.CategoriesExportDTO;
import com.example.xmlexercise.service.CategoryService;
import com.example.xmlexercise.service.ProductService;
import com.example.xmlexercise.service.SeedService;
import com.example.xmlexercise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

@Component
public class ShopRunner implements CommandLineRunner {

    private final SeedService seedService;
    private final ProductService productService;
    private final UserService userService;
    private final CategoryService categoryService;

    @Autowired
    public ShopRunner(SeedService seedService, ProductService productService,
                      UserService userService, CategoryService categoryService) {
        this.seedService = seedService;
        this.productService = productService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.userService.getUsersAndProducts();
    }

//        1    ProductsExportInRangeDTO productExportDTOs = this.productService.getProductsInRange(500f, 1000f);
//
//        JAXBContext context = JAXBContext.newInstance(ProductsExportInRangeDTO.class);
//        Marshaller marshaller = context.createMarshaller();
//        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//
//        marshaller.marshal(productExportDTOs, System.out);
//
//    2
//    UserExportDTO userExportDTO = this.userService.getUsersWithSuccessfullySoldProducts();
//
//    JAXBContext context = JAXBContext.newInstance(UserExportDTO.class);
//    Marshaller marshaller = context.createMarshaller();
//        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//
//        marshaller.marshal(userExportDTO, System.out);
//
//    3
//    CategoriesExportDTO categoryStats = this.categoryService.getCategoryStats();
//
//    JAXBContext context = JAXBContext.newInstance(CategoriesExportDTO.class);
//    Marshaller marshaller = context.createMarshaller();
//        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//
//        marshaller.marshal(categoryStats, System.out);
}
