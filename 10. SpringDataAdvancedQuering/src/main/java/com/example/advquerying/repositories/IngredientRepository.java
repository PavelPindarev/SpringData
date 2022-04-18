package com.example.advquerying.repositories;

import com.example.advquerying.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    List<Ingredient> findIngredientByNameStartingWith(String symbol);

    List<Ingredient> findIngredientsByNameIn(List<String> names);

    @Modifying
    @Transactional
    @Query("UPDATE Ingredient i " +
            " SET i.price = i.price + i.price * :percent")
    void updatePriceByPercent(BigDecimal percent);

}
