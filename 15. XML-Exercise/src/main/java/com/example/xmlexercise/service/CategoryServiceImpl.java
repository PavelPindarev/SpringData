package com.example.xmlexercise.service;

import com.example.xmlexercise.entity.category.CategoriesExportDTO;
import com.example.xmlexercise.entity.category.CategoryStatsDTO;
import com.example.xmlexercise.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoriesExportDTO getCategoryStats() {
        List<CategoryStatsDTO> categoryStatsDTO = this.categoryRepository.getCategoryStatsDTO();

        return new CategoriesExportDTO(categoryStatsDTO);
    }
}
