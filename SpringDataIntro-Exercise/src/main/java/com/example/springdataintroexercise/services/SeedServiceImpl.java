package com.example.springdataintroexercise.services;

import com.example.springdataintroexercise.models.*;
import com.example.springdataintroexercise.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SeedServiceImpl implements SeedService {

    private static final String RESOURCE_PATH = "src/main/resources/files/";

    private static final String AUTHORS_FILE_PATH = RESOURCE_PATH + "authors.txt";
    private static final String BOOKS_FILE_PATH = RESOURCE_PATH + "books.txt";
    private static final String CATEGORIES_FILE_PATH = RESOURCE_PATH + "categories.txt";

    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;
    private final CategoryService categoryService;
    private final AuthorService authorService;

    @Autowired
    public SeedServiceImpl(AuthorRepository authorRepository, CategoryRepository categoryRepository,
                           BookRepository bookRepository, CategoryService categoryService, AuthorService authorService) {
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.bookRepository = bookRepository;
        this.categoryService = categoryService;
        this.authorService = authorService;
    }

    @Override
    public void seedAuthors() throws IOException {
        Files.readAllLines(Path.of(AUTHORS_FILE_PATH))
                .stream()
                .filter(a -> !a.isBlank())
                .map(s -> s.split("\\s+"))
                .map(names -> new Author(names[0], names[1]))
                .forEach(authorRepository::save);
    }

    @Override
    public void seedCategories() throws IOException {
        Files.readAllLines(Path.of(CATEGORIES_FILE_PATH))
                .stream()
                .filter(a -> !a.isBlank())
                .map(Category::new)
                .forEach(categoryRepository::save);
    }

    @Override
    public void seedBooks() throws IOException {
        Files.readAllLines(Path.of(BOOKS_FILE_PATH))
                .stream()
                .filter(a -> !a.isBlank())
                .map(this::getBookObject)
                .forEach(bookRepository::save);

    }

    private Book getBookObject(String line) {
        String[] tokens = line.split("\\s+");

        EditionType editionType = EditionType.values()[Integer.parseInt(tokens[0])];

        LocalDate releaseDate = LocalDate.parse(tokens[1], DateTimeFormatter.ofPattern("d/M/yyyy"));

        int copies = Integer.parseInt(tokens[2]);

        BigDecimal price = new BigDecimal(tokens[3]);

        AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(tokens[4])];

        String title = Arrays.stream(tokens)
                .skip(5)
                .collect(Collectors.joining(" "));

        Author author = authorService.getRandomAuthor();
        Set<Category> categories = categoryService.getRandomCategories();

        return new Book(title, editionType, price, copies, releaseDate, ageRestriction, author, categories);
    }


}

