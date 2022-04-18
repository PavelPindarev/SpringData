package com.example.xmlexercise.repository;

import com.example.xmlexercise.entity.category.Category;
import com.example.xmlexercise.entity.category.CategoryStatsDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("""
            SELECT new com.example.xmlexercise.entity.category.CategoryStatsDTO
            (c.name, c.products.size, AVG (p.price), SUM (p.price))
                        FROM categories c
                        JOIN c.products p
                        GROUP BY c.name
                        ORDER BY c.products.size DESC
                        """)
    List<CategoryStatsDTO> getCategoryStatsDTO();

}
