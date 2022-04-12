package com.example.xmlexercise.service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public interface SeedService {

    void seedCategories() throws FileNotFoundException, JAXBException;

    void seedUsers() throws FileNotFoundException, JAXBException;

    void seedProducts() throws FileNotFoundException, JAXBException;

    default void seedAll() throws JAXBException, FileNotFoundException {
        seedCategories();
        seedUsers();
        seedProducts();
    }
}
