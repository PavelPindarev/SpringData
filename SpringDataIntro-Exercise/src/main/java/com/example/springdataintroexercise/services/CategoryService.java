package com.example.springdataintroexercise.services;

import com.example.springdataintroexercise.models.Category;

import java.util.Set;

public interface CategoryService {
    Set<Category> getRandomCategories();
}
