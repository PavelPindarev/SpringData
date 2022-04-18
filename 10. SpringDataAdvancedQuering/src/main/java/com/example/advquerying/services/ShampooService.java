package com.example.advquerying.services;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface ShampooService {
    List<Shampoo> selectShampoosBySize(Size size);

    List<Shampoo> selectShampoosBySizeOrLabelId(Size size, Long labelId);

    List<Shampoo> selectShampoosByPriceHigherThan(BigDecimal price);

    int countShampoosByPriceLowerThan(BigDecimal price);

    Set<String> selectShampoosByIngredients(List<String> ingredients);

    List<Shampoo> selectShampoosByIngredientsCount(int ingredientsCount);
}
