package com.example.advquerying.repositories;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Repository
public interface ShampooRepository extends JpaRepository<Shampoo, Long> {

    List<Shampoo> findShampooBySize(Size size);

    List<Shampoo> findShampooBySizeOrLabelId(Size size, Long labelId);

    List<Shampoo> findShampooByPriceGreaterThanOrderByPriceDesc(BigDecimal price);

    int countShampooByPriceLessThan(BigDecimal price);

    @Query("SELECT DISTINCT s.brand From Shampoo as s" +
            " JOIN s.ingredients as i" +
            " WHERE i.name IN :ingredientNames")
    Set<String> findShampoosByIngredients(List<String> ingredientNames);


    @Query("SELECT s FROM Shampoo as s" +
            " WHERE s.ingredients.size < :ingredientsCount")
    List<Shampoo> findShampoosByIngredientsCountLessThan(int ingredientsCount);
}
