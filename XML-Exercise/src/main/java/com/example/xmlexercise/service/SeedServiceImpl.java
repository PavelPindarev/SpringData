package com.example.xmlexercise.service;

import com.example.xmlexercise.entity.category.CategoriesImportDTO;
import com.example.xmlexercise.entity.category.Category;
import com.example.xmlexercise.entity.product.Product;
import com.example.xmlexercise.entity.product.ProductsImportDTO;
import com.example.xmlexercise.entity.user.User;
import com.example.xmlexercise.entity.user.UsersImportDTO;
import com.example.xmlexercise.repository.CategoryRepository;
import com.example.xmlexercise.repository.ProductRepository;
import com.example.xmlexercise.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SeedServiceImpl implements SeedService {

    private final String CATEGORIES_PATH = "src/main/resources/productsShopResources/categories.xml";
    private final String USERS_PATH = "src/main/resources/productsShopResources/users.xml";
    private final String PRODUCTS_PATH = "src/main/resources/productsShopResources/products.xml";

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    private ModelMapper mapper;

    @Autowired
    public SeedServiceImpl(CategoryRepository categoryRepository,
                           UserRepository userRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        mapper = new ModelMapper();
    }

    @Override
    public void seedCategories() throws FileNotFoundException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(CategoriesImportDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        FileReader fileReader = new FileReader(CATEGORIES_PATH);

        CategoriesImportDTO categoryDTOs = (CategoriesImportDTO) unmarshaller.unmarshal(fileReader);

        List<Category> categories = categoryDTOs.getCategories()
                .stream()
                .map(dto -> mapper.map(dto, Category.class))
                .collect(Collectors.toList());

        categoryRepository.saveAll(categories);
    }

    @Override
    public void seedUsers() throws FileNotFoundException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(UsersImportDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        FileReader fileReader = new FileReader(USERS_PATH);

        UsersImportDTO usersDTOs = (UsersImportDTO) unmarshaller.unmarshal(fileReader);

        List<User> users = usersDTOs.getUserImportDTOs()
                .stream()
                .map(dto -> mapper.map(dto, User.class))
                .collect(Collectors.toList());

        userRepository.saveAll(users);
    }

    @Override
    public void seedProducts() throws FileNotFoundException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(ProductsImportDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        FileReader fileReader = new FileReader(PRODUCTS_PATH);

        ProductsImportDTO productsDTOs = (ProductsImportDTO) unmarshaller.unmarshal(fileReader);

        List<Product> products = productsDTOs.getProductImportDTOS()
                .stream()
                .map(dto -> mapper.map(dto, Product.class))
                .map(this::setRandomCategories)
                .map(this::setRandomBuyer)
                .map(this::setRandomSeller)
                .collect(Collectors.toList());

        productRepository.saveAll(products);
    }

    private Product setRandomCategories(Product product) {
        Random random = new Random();
        long categoriesDbCount = this.categoryRepository.count();

        int count = random.nextInt((int) categoriesDbCount);

        Set<Category> categories = new HashSet<>();
        for (int i = 0; i < count; i++) {
            int randomId = random.nextInt((int) categoriesDbCount) + 1;

            Optional<Category> randomCategory = this.categoryRepository.findById(randomId);

            categories.add(randomCategory.get());
        }

        product.setCategories(categories);
        return product;
    }

    private Product setRandomBuyer(Product product) {
        if (product.getPrice().compareTo(BigDecimal.valueOf(944)) > 0) {
            return product;
        }
        User randomUser = getRandomUser().get();

        product.setBuyer(randomUser);
        return product;
    }

    private Product setRandomSeller(Product product) {
        User randomUser = getRandomUser().get();

        product.setSeller(randomUser);
        return product;
    }

    private Optional<User> getRandomUser() {
        long usersCount = this.userRepository.count();

        int randomUserId = new Random().nextInt((int) usersCount) + 1;

        Optional<User> user = this.userRepository.findById(randomUserId);

        return user;
    }

}
