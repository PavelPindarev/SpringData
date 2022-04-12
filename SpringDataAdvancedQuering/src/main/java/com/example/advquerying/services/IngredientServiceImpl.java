package com.example.advquerying.services;

import com.example.advquerying.entities.Ingredient;
import com.example.advquerying.repositories.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public List<Ingredient> selectIngredientsByNameStartWith(String symbol) {

        return ingredientRepository.findIngredientByNameStartingWith(symbol);
    }

    @Override
    public List<Ingredient> selectIngredientsByNames(List<String> ingredientNames) {
        return ingredientRepository.findIngredientsByNameIn(ingredientNames);
    }

    @Override
    public void updateIngredientsPriceByPercent(BigDecimal percent) {
        ingredientRepository.updatePriceByPercent(percent);
    }
}
