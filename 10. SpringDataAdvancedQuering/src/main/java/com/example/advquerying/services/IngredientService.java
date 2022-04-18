package com.example.advquerying.services;

import com.example.advquerying.entities.Ingredient;

import java.math.BigDecimal;
import java.util.List;

public interface IngredientService {
    List<Ingredient> selectIngredientsByNameStartWith(String symbol);

    List<Ingredient> selectIngredientsByNames(List<String> ingredientNames);

    void updateIngredientsPriceByPercent(BigDecimal percent);
}
